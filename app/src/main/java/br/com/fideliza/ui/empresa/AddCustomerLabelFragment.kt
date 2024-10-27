package br.com.fideliza.ui.empresa

import Cliente
import ClienteAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fideliza.R
import br.com.fideliza.databinding.FragmentAddCustomerLabelBinding
import br.com.fideliza.servidor.ConexaoServidor
import br.com.fideliza.servidor.ServerCallback
import org.bson.Document

class AddCustomerLabelFragment : Fragment(R.layout.fragment_add_customer_label), ServerCallback {

    private var _binding: FragmentAddCustomerLabelBinding? = null
    private val binding get() = _binding!!
    private lateinit var ret: Document

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCustomerLabelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBuscar.setOnClickListener {

            val docBusca = Document.parse("{ \"cpf\" : \"${binding.etCPF.text.toString().trim()}\" }")
            Log.d("ConsultaCPF", "Enviando consulta para o servidor: ${docBusca.toJson()}")
            ConexaoServidor.conexao("3;${docBusca.toJson()};clientes", this)

            // Configurar o RecyclerView
            val recyclerView = binding.recyclerViewClientes
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResult(resultado: String?) {
        try {
            if (resultado == null || resultado.contains("Nenhum documento encontrado.") || resultado.contains("Erro")) {
                // Exibir mensagem de feedback se nenhum documento for encontrado ou houver erro
                activity?.runOnUiThread {
                    Toast.makeText(requireContext(), "Nenhum cliente encontrado ou erro no servidor.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Tentar fazer o parsing apenas se o JSON for válido
                try {
                    ret = Document.parse(resultado)

                    // Atualizar RecyclerView na UI principal
                    activity?.runOnUiThread {
                        val clientes = listOf(
                            Cliente(
                                ret.get("nome").toString(),
                                ret.get("email").toString(),
                                ret.get("telefone").toString(),
                                ret.get("cpf").toString(),
                                ret.get("firebaseUID").toString()
                            )
                        )

                        val recyclerView = binding.recyclerViewClientes
                        val adapter = ClienteAdapter(clientes) { cliente ->
                            // Navega para o AddLabelCustomerFragment ao clicar no botão do card
                            val action =
                                AddCustomerLabelFragmentDirections.actionAddCustomerLabelFragmentToAddLabelCustomer(cliente.cpf)
                            findNavController().navigate(action)
                        }

                        // Atribuir o Adapter ao RecyclerView
                        recyclerView.adapter = adapter
                    }
                } catch (e: Exception) {
                    Log.e("ERRS", "Erro ao fazer parsing do JSON: ${e.message}")
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), "Erro ao processar os dados recebidos.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ERRS", e.message.toString())
            activity?.runOnUiThread {
                Toast.makeText(requireContext(), "Erro inesperado ao processar a resposta.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
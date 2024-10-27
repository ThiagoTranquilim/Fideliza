package br.com.fideliza.ui.empresa

import Cliente
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fideliza.R
import br.com.fideliza.data.ClienteAdapter
import br.com.fideliza.databinding.FragmentAddCustomerLabelBinding
import br.com.fideliza.servidor.ConexaoServidor
import br.com.fideliza.servidor.ServerCallback
import org.bson.Document

class AddCustomerLabelFragment : Fragment(R.layout.fragment_add_customer_label), ServerCallback {

    private var _binding: FragmentAddCustomerLabelBinding? = null
    private val binding get() = _binding!!
    private lateinit var ret : Document

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

            var docBusca = Document.parse("{ \"cpf\" : \"${binding.etCPF.text.toString()}\" }")

            ConexaoServidor.conexao("3;${docBusca};clientes", this);

            // Configurar o RecyclerView
            val recyclerView = binding.recyclerViewClientes
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

        }

        /*

        // Lista de clientes
        val clientes = listOf(
            Cliente("João Silva"),
            Cliente("Maria Oliveira"),
            Cliente("Carlos Santos"),
            Cliente("Ana Paula")
        )

        // Adapter para exibir os clientes
        val adapter = ClienteAdapter(clientes) { cliente ->
            // Navega para o AddLabelCustomerFragment, passando o nome do cliente
            val action =
                br.com.fideliza.ui.empresa.AddCustomerLabelFragmentDirections.actionAddCustomerLabelFragmentToAddLabelCustomer(
                    cliente.nome
                )
            findNavController().navigate(action)
        }

        // Atribuir o Adapter ao RecyclerView
        recyclerView.adapter = adapter


         */
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
            ret = Document.parse(resultado);
        }catch (e : Exception) {
            Log.e("ERRS", e.message.toString())
        }
    }
}


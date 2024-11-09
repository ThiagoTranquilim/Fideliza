package br.com.fideliza.ui.empresa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import br.com.fideliza.R
import br.com.fideliza.databinding.FragmentAddLabelCustomerBinding
import br.com.fideliza.servidor.ConexaoServidor
import br.com.fideliza.servidor.ServerCallback
import com.google.firebase.auth.FirebaseAuth
import org.bson.Document

class AddLabelCustomer : Fragment(R.layout.fragment_add_label_customer), ServerCallback {

    private var _binding: FragmentAddLabelCustomerBinding? = null
    private val binding get() = _binding!!
    private lateinit var ret: Document
    private lateinit var firebaseAuth: FirebaseAuth

    // Utilizando Safe Args para receber o argumento CPF
    private val args: AddLabelCustomerArgs by navArgs()
    private lateinit var cpf: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddLabelCustomerBinding.bind(view)  // Inicialização do binding

        // Recuperando o CPF passado no argumento
        cpf = args.cpf
        Log.i("CPF", cpf)

        firebaseAuth = FirebaseAuth.getInstance()

        // Obter referências para os elementos do layout
        val btnLancarSelo = binding.btnLancarSelo
        val btnVoltar = binding.btnVoltar
        val etValor = binding.etValor
        val etData = binding.etData
        val etDescricao = binding.etDescricao

        ConexaoServidor.conexao("3;{ \"cpf\" : \"${cpf}\" };clientes", this)

        // Ação ao clicar no botão "Lançar Selo"
        btnLancarSelo.setOnClickListener {
            val valor = etValor.text.toString()
            val data = etData.text.toString()
            val descricao = etDescricao.text.toString()

            val doc = Document()
            doc.append("valor", valor).append("descricao", descricao)

            // Chamadas para o servidor
            ConexaoServidor.conexao("2;${firebaseAuth.uid};${cpf};${doc.toJson()}", this@AddLabelCustomer)
        }

        // Ação ao clicar no botão "Voltar"
        btnVoltar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onResult(resposta: String) {
        Log.i("Resp", resposta)
        try {
            if (resposta == "Sucesso") {
                Toast.makeText(requireContext(), "Selo lançado com sucesso", Toast.LENGTH_SHORT)
                    .show()
            } else {
                ret = Document.parse(resposta)
                Log.i("ParsedDocument", ret.toJson())

                // Verifique se o fragmento está adicionado antes de atualizar o binding
                if (isAdded) {
                    binding.tvNomeCliente.text = ret.getString("nome") ?: "Nome não encontrado"
                } else {
                    Log.e("ERRS", "Fragment não está mais anexado à Activity")
                }
            }
        } catch (e: Exception) {
            Log.e("ERRS", e.message.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
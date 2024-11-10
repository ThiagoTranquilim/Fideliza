package br.com.fideliza.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.fideliza.databinding.FragmentProfileBinding
import br.com.fideliza.servidor.ConexaoServidor
import br.com.fideliza.servidor.ServerCallback
import com.google.firebase.auth.FirebaseAuth
import org.bson.Document

class Profile : Fragment(), ServerCallback {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnVoltar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUID = firebaseAuth.uid ?: return

        // Primeira tentativa: buscar como cliente
        buscarDocumento("clientes")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun buscarDocumento(colecao: String,) {
        // Monta o documento com o Firebase UID
        val doc = Document().append("firebaseUID", firebaseAuth.uid)

        // Chama o servidor passando a operação, o documento em JSON e a coleção
        ConexaoServidor.conexao("3;${doc.toJson()};$colecao", this)
    }

    override fun onResult(resultado: String) {
        if (resultado == "nenhum documento encontrado.") {
            // Se o resultado da busca em "clientes" não encontrou nada, tenta em "empresas"
            buscarDocumento("empresas")
        } else {
            try {
                val doc = Document.parse(resultado)

                // Depuração: imprime o documento para verificar o conteúdo
                println("Documento recebido: $doc")

                // Verifica se é cliente (tem CPF) ou empresa (tem CNPJ)
                if (doc.containsKey("cpf") && doc.getString("cpf") != null) {
                    atualizarUIComDados(doc, isCliente = true)
                } else if (doc.containsKey("cnpj") && doc.getString("cnpj") != null) {
                    atualizarUIComDados(doc, isCliente = false)
                } else {
                    // Caso o documento não contenha CPF nem CNPJ, pode ser algum erro inesperado
                    exibirMensagemErro("Documento inválido")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                exibirMensagemErro("Erro ao processar o documento")
            }
        }
    }

    private fun exibirMensagemErro(mensagem: String) {
        requireActivity().runOnUiThread {
            // Exibe uma mensagem de erro na tela, por exemplo, em um Toast
            Toast.makeText(requireContext(), mensagem, Toast.LENGTH_LONG).show()
        }
    }

    private fun atualizarUIComDados(doc: Document, isCliente: Boolean) {
        // Atualiza os dados na thread principal
        requireActivity().runOnUiThread {
            val nome = doc.getString("nome")
            val email = doc.getString("email")
            val documento = if (isCliente) doc.getString("cpf") else doc.getString("cnpj")

            binding.tvNomeValor.text = nome
            binding.tvEmailValor.text = email
            binding.tvDocumento.text = if (isCliente) "CPF:" else "CNPJ:"
            binding.tvDocumentoValor.text = documento
        }
    }
}
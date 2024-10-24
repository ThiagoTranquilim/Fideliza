package br.com.fideliza.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.fideliza.databinding.FragmentRegisterBinding
import br.com.fideliza.firebase.auth.AuthCallBack
import br.com.fideliza.firebase.auth.FirebaseAuthManager
import br.com.fideliza.servidor.ConexaoServidor
import br.com.fideliza.servidor.ServerCallback
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import org.bson.Document
import java.lang.Exception

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuthManager: FirebaseAuthManager
    private lateinit var firebaseUser: FirebaseUser

    private lateinit var nome: String
    private lateinit var cpf: String
    private lateinit var telefone: String
    private lateinit var clienteDocument: Document

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuthManager = FirebaseAuthManager()

        binding.btnCadastrarSe.setOnClickListener {

            // Obtenção dos dados do formulário
            val emailEditText = binding.etEmailCadastro.text.toString()
            val senhaEditText = binding.etSenhaCadastro.text.toString()
            nome = binding.Nome.text.toString()
            telefone = "11-970707070"  // Ajuste para obter da UI
            cpf = "1111111111"  // Ajuste para obter da UI

            // Chama o método de cadastro
            cadastrar(emailEditText, senhaEditText)
        }

        binding.btnVoltar.setOnClickListener {
            // Voltar para a tela anterior ou outra navegação
            val action = RegisterFragmentDirections.registerCompanyFragmentDirections()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun cadastrar(email: String, password: String) {
        firebaseAuthManager.createAccount(email, password, object : AuthCallBack() {
            override fun createSuccess(result: AuthResult?) {
                firebaseUser = result?.user ?: return

                // Agora que temos o firebaseUID, criar o Document
                clienteDocument = Document().apply {
                    append("nome", nome)
                    append("email", email)
                    append("telefone", telefone)
                    append("cpf", cpf)
                    append("firebaseUID", firebaseUser.uid)  // Adiciona o firebaseUID
                }

                // Enviar os dados para o servidor com o firebaseUID usando a interface ServerCallback
                ConexaoServidor.conexao("1;${clienteDocument.toJson()};clientes", object : ServerCallback {
                    override fun onResult(resposta: String) {
                        activity?.runOnUiThread {
                            if (resposta.contains("sucesso")) {
                                Toast.makeText(context, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                                // Navegar para a próxima tela de verificação de email
                                val action = RegisterFragmentDirections.actionRegisterFragmentToVerificationFragment(firebaseUser.uid)
                                findNavController().navigate(action)
                            } else {
                                Toast.makeText(context, "Falha ao cadastrar cliente: $resposta", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }

            override fun onFailure(exception: Exception?) {
                super.onFailure(exception)
                activity?.runOnUiThread {
                    Toast.makeText(context, "Falha ao criar novo usuário: ${exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
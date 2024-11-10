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

class RegisterFragment : Fragment(), ServerCallback {

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
            val senhaNovamente = binding.etSenhaNovamente.text.toString()
            nome = binding.Nome.text.toString()
            telefone = binding.etTelefone.text.toString()
            cpf = binding.etCPF.text.toString()

            //Verificamos o preenchimento dos campos
            if (emailEditText.isEmpty()) {
                binding.etEmailCadastro.error = "Informe o e-mail"
            } else if (senhaEditText.isEmpty()) {
                binding.etSenhaCadastro.error = "Informe a senha"
            } else if (senhaNovamente.isEmpty()) {
                binding.etSenhaNovamente.error = "Confirme a senha"
            } else if (senhaEditText != senhaNovamente) {
                binding.etSenhaNovamente.error = "As senhas são diferentes"
            } else if (nome.isEmpty()) {
                binding.Nome.error = "Informe o nome"
            } else if (telefone.isEmpty()) {
                binding.etTelefone.error = "Informe o telefone"
            } else if (cpf.isEmpty()) {
                binding.etCPF.error = "Informe o CPF"
            } else {
                //Chama o método para cadastrar
                cadastrar(emailEditText, senhaEditText)
            }
        }

        binding.btnCadastrarEmpresa.setOnClickListener {
            val action = RegisterFragmentDirections.registerCompanyFragmentDirections()
            findNavController().navigate(action)
        }

        binding.btnVoltar.setOnClickListener {
            // Voltar para a tela anterior ou outra navegação
            val action = RegisterFragmentDirections.actionRegisterFragmentPop();
            findNavController().navigate(action);

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
                ConexaoServidor.conexao("1;${clienteDocument.toJson()};clientes", this@RegisterFragment)
            }

            override fun onFailure(exception: Exception?) {
                super.onFailure(exception)
                activity?.runOnUiThread {
                    Toast.makeText(context, "Falha ao criar novo usuário: ${exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onResult(resposta: String) {
        activity?.runOnUiThread {
            if (resposta.contains("Sucesso")) {
                Toast.makeText(context, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                // Navegar para a próxima tela de verificação de email
                val action = RegisterFragmentDirections.actionRegisterFragmentToVerificationFragment(firebaseUser.uid)
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Falha ao cadastrar cliente: $resposta", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
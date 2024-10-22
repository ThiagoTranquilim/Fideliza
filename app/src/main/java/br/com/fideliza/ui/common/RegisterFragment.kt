package br.com.fideliza.ui.common

import android.os.Bundle
import android.util.Log
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
import br.com.fideliza.servidor.Gerenciador
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import org.bson.Document
import org.bson.conversions.Bson
import java.lang.Exception
import java.util.concurrent.Executors

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuthManager : FirebaseAuthManager
    private lateinit var firebaseUser: FirebaseUser

    private lateinit var nome : String;
    private lateinit var email: String;
    private lateinit var cpf: String;
    private lateinit var telefone: String;
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

            // Aqui devemos implementar a função para cadastrar o usuário
            val emailEditText = binding.etEmailCadastro.text.toString()
            val senhaEditText = binding.etSenhaCadastro.text.toString()
            nome = binding.Nome.text.toString();
            telefone = "11-970707070";
            cpf = "1111111111";

            cadastrar(emailEditText, senhaEditText)

            clienteDocument = Document()
            clienteDocument.append("nome", nome);
            clienteDocument.append("email", emailEditText)
            clienteDocument.append("telefone", telefone);
            clienteDocument.append("cpf", cpf);

            /*
            Executors.newSingleThreadExecutor().execute {
                val resposta = ConexaoServidor.conexao("1;" + clienteDocument.toJson() +";clientes")
                Log.d("Teste", resposta)
            }
             */
            var gerenciador : Gerenciador = Gerenciador("1;" + clienteDocument.toJson() +";clientes")


        }


        binding.btnVoltar.setOnClickListener {
            //parentFragmentManager.popBackStack()
            // Navegar para a próxima tela, se necessário
            val action = RegisterFragmentDirections
                .registerCompanyFragmentDirections()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun cadastrar(email: String, password: String) {
        firebaseAuthManager.createAccount(email, password, object: AuthCallBack() {
            override fun createSuccess(result: AuthResult?) {
                firebaseUser = result?.user ?: return
                val action = RegisterFragmentDirections
                    .actionRegisterFragmentToVerificationFragment(firebaseUser.uid)
                findNavController().navigate(action)

            }

            override fun onFailure(exception: Exception?) {
                super.onFailure(exception)
                activity?.runOnUiThread {
                    Toast.makeText(context, "Falha ao criar novo usuário: ${exception?.message}", Toast.LENGTH_SHORT).show();
                }
            }
        })
    }
}
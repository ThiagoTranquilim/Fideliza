package br.com.fideliza.ui.common

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.fideliza.R
import br.com.fideliza.databinding.FragmentLoginBinding
import br.com.fideliza.firebase.auth.AuthCallBack
import br.com.fideliza.firebase.auth.FirebaseAuthManager
import br.com.fideliza.servidor.ConexaoServidor
import br.com.fideliza.servidor.ServerCallback
import com.google.firebase.auth.FirebaseUser
import org.bson.Document
import org.bson.json.JsonParseException

class LoginFragment : Fragment(), ServerCallback {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuthManager : FirebaseAuthManager
    private var ret: String? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuthManager = FirebaseAuthManager()

        // Limpar campos de entrada ao exibir o fragmento
        binding.etEmail.text?.clear()
        binding.etSenha.text?.clear()

        binding.btnCadastrar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnEntrar.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val senha = binding.etSenha.text.toString()
            if (email.isNotBlank() && senha.isNotBlank()) {
                login(email, senha)
            } else {
                Toast.makeText(context, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnEsqueciASenha.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverPassword)
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_global_to_homeFragment) {
            popUpTo(R.id.nav_graph) {
                inclusive = true
            }
        }
    }
    private fun login(email: String, password: String) {
        firebaseAuthManager.login(email, password, object: AuthCallBack() {
            override fun onSuccess(user: FirebaseUser?) {
                if (user?.isEmailVerified == true) {
                    val loginDoc = Document("firebaseUID", user.uid.toString())
                    ConexaoServidor.conexao("3;${loginDoc.toJson()};clientes", this@LoginFragment)
                } else {
                    val action = LoginFragmentDirections.actionLoginFragmentToVerificationFragment(user!!.uid)
                    findNavController().navigate(action)
                }
            }

            override fun onFailure(exception: Exception?) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "Falha ao realizar o login: ${exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onResult(resultado: String) {
        activity?.runOnUiThread {
            Log.i("LoginFragment", "Resultado recebido: '$resultado'")

            if (!isAdded || findNavController().currentDestination?.id != R.id.loginFragment) {
                Log.w("LoginFragment", "Fragment não está mais ativo ou NavController não está no destino correto")
                return@runOnUiThread
            }

            if (resultado.trim() == "nenhum documento encontrado.") {
                findNavController().navigate(R.id.action_loginFragment_to_companyProfile)
            } else {
                try {
                    val document = Document.parse(resultado)
                    findNavController().navigate(R.id.action_loginFragment_to_customerMenuFragment)
                } catch (e: JsonParseException) {
                    Log.e("LoginFragment", "Erro ao parsear JSON: ${e.message}")
                    Toast.makeText(context, "Erro ao processar os dados recebidos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
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

        binding.Cadastrar.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }

        // Aqui devemos verificar que tipo de usuário se conectou e então determinar qual fragment devemos abrir company ou customer
        binding.btnEntrar.setOnClickListener {

            val emailEditText = binding.etEmail.text.toString()
            val passwordEditText = binding.etSenha.text.toString()
            login(emailEditText, passwordEditText)
        }


        binding.btnEsqueciASenha.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_recoverPassword) }
    }

    private fun login(email: String, password: String) {
        firebaseAuthManager.login(email, password, object: AuthCallBack() {
            override fun onSuccess(user : FirebaseUser?) {
                if (user?.isEmailVerified == true) {
                    activity?.runOnUiThread {
                        val loginDoc = Document("firebaseUID", user.uid)
                        ConexaoServidor.conexao("3;${loginDoc.toJson()};clientes", this@LoginFragment);

                        while(ret == null);
                        // Aqui você pode adicionar a lógica de navegação, pois terá certeza de que `ret` foi atualizado
                        if (ret.equals("Nenhum documento encontrado.")) {
                            findNavController().navigate(R.id.action_loginFragment_to_companyProfile)
                        } else {
                            findNavController().navigate(R.id.action_loginFragment_to_customerMenuFragment)
                        }
                    }
                } else {
                    val action = LoginFragmentDirections.actionLoginFragmentToVerificationFragment(user!!.uid)
                    findNavController().navigate(action)
                }
            }
            override fun onFailure (exception : Exception?) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "Falha ao realizar o login: ${exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResult(resultado: String) {
        ret = resultado
        Log.i("BILU", ret.toString())

        /*
        // Aqui você pode adicionar a lógica de navegação, pois terá certeza de que `ret` foi atualizado
        if (ret.equals("Nenhum documento encontrado.")) {
            findNavController().navigate(R.id.action_loginFragment_to_companyProfile)
        } else {
            findNavController().navigate(R.id.action_loginFragment_to_customerMenuFragment)
        }
         */
    }
}
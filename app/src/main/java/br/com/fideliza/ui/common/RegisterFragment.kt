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
import br.com.fideliza.databinding.FragmentRegisterBinding
import br.com.fideliza.firebase.auth.AuthCallBack
import br.com.fideliza.firebase.auth.FirebaseAuthManager
import br.com.fideliza.servidor.ConexaoServidor
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception
import java.util.concurrent.Executors

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuthManager : FirebaseAuthManager
    private lateinit var firebaseUser: FirebaseUser

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

        activity?.runOnUiThread {
            Executors.newSingleThreadExecutor().execute {
                val resposta = ConexaoServidor.conecao("2;123;123")
                activity?.runOnUiThread {
                    Log.d("TESTE", resposta)
                    }
                }
        }
        binding.btnCadastrarSe.setOnClickListener {

            // Aqui devemos implementar a função para cadastrar o usuário
            val emailEditText = binding.etEmailCadastro.text.toString()
            val senhaEditText = binding.etSenhaCadastro.text.toString()

            cadastrar(emailEditText, senhaEditText)
        }


        binding.btnVoltar.setOnClickListener {
            parentFragmentManager.popBackStack()
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
package br.com.fideliza.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.fideliza.MainActivity
import br.com.fideliza.R
import br.com.fideliza.databinding.FragmentRecoverPasswordBinding
import br.com.fideliza.firebase.auth.AuthCallBack
import br.com.fideliza.firebase.auth.FirebaseAuthManager
import java.lang.Exception

class RecoverPassword : Fragment() {

    private var _binding: FragmentRecoverPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuthManager : FirebaseAuthManager

    private lateinit var editTextEmail : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRecoverPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuthManager = (activity as MainActivity).getFirebaseAuth()


        // Aqui devemos verificarenviar um email para recuperar a senha
        binding.btnRecuperarSenha.setOnClickListener {
            editTextEmail = binding.etEmailCadastrado.text.toString()
            recoveryPassword(editTextEmail)

        }


        binding.btnCancelar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun recoveryPassword(email: String) {
        firebaseAuthManager.recoverPassword(email, object: AuthCallBack () {
            override fun resetPasswordSuccess() {
                activity?.runOnUiThread(){
                    Toast.makeText(context, "Email de recuperação de senha enviado.", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_recoverPassword_pop)
                }
            }

            override fun onFailure(exception: Exception?) {
                activity?.runOnUiThread() {
                    Toast.makeText(context, "${email}", Toast.LENGTH_SHORT).show()
                }
             }
        })
    }

}
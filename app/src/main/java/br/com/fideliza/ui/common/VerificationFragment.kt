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
import br.com.fideliza.databinding.FragmentVerificationBinding
import br.com.fideliza.firebase.auth.AuthCallBack
import br.com.fideliza.firebase.auth.FirebaseAuthManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

class VerificationFragment : Fragment() {

    private var _binding: FragmentVerificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuthManager : FirebaseAuthManager
    private var firebaseUser : FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseAuthManager = (activity as MainActivity).getFirebaseAuth()
        val uid = arguments?.getString("userUid")

        firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser != null && firebaseUser!!.uid == uid) {
            verifyEmail(firebaseUser!!)
        }


        // implementar a verificação do tipo de checkbox e então saber se vai para o fragment do código de sms ou para o login
        /*
        binding.btnVerificar.setOnClickListener {
            val selectedOption = when (binding.radioGroupVerification.checkedRadioButtonId) {
                R.id.radioButtonEmail -> "email"
                R.id.radioButtonSMS -> "sms"
                else -> null
            }

            if (selectedOption == "email") {
                if (firebaseUser != null && firebaseUser!!.uid == uid) {
                    verifyEmail(firebaseUser!!)
                }
                findNavController().navigate(R.id.action_verificationFragment_to_loginFragment)
            } else if (selectedOption == "sms") {
                findNavController().navigate(R.id.action_verificationFragment_to_SMSReception)
            }

        }*/

        binding.btnVoltar.setOnClickListener {
            findNavController().navigate(R.id.action_verificationFragment_to_loginFragment)
            //parentFragmentManager.popBackStack()
        }
    }

    fun verifyEmail (user: FirebaseUser) {
        firebaseAuthManager.verifyEmail(user, object : AuthCallBack() {
            override fun verifyEmailSuccess() {
                activity?.runOnUiThread() {
                    Toast.makeText(context, "Verifique seu email.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(exception: Exception?) {
                activity?.runOnUiThread() {
                    Toast.makeText(context, "Email não enviado, erro: ${exception?.message}.",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
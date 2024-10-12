package br.com.fideliza.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.fideliza.R
import br.com.fideliza.databinding.FragmentVerificationBinding

class VerificationFragment : Fragment() {

    private var _binding: FragmentVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // implementar a verificação do tipo de checkbox e então saber se vai para o fragment do código de sms ou para o login
        binding.btnVerificar.setOnClickListener {

            findNavController().navigate(R.id.action_verificationFragment_to_SMSReception)

        }

        binding.btnVoltar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
package br.com.fideliza.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.fideliza.R
import br.com.fideliza.databinding.FragmentSmsReceptionBinding

class SMSReception : Fragment() {

    private var _binding: FragmentSmsReceptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSmsReceptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // implementar função para verificar o código sms
        binding.btnVerify.setOnClickListener {

            findNavController().navigate(R.id.action_SMSReception_to_loginFragment)
        }

        binding.btnCancelar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
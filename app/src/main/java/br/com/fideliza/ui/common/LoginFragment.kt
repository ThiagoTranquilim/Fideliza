package br.com.fideliza.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.fideliza.R
import br.com.fideliza.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.Cadastrar.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }

        // Aqui devemos verificar que tipo de usuário se conectou e então determinar qual fragment devemos abrir company ou customer
        binding.btnEntrar.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_companyProfile)
        }


        binding.btnEsqueciASenha.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_recoverPassword) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
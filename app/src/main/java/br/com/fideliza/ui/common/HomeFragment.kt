package br.com.fideliza.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.fideliza.R
import br.com.fideliza.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEntrar.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_loginFragment) }
        binding.btnCadastrarSe.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_registerFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
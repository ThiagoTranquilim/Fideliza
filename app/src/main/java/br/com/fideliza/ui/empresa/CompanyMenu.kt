package br.com.fideliza.ui.empresa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.fideliza.R
import br.com.fideliza.databinding.FragmentCompanyMenuBinding

class CompanyMenu : Fragment() {

    private var _binding: FragmentCompanyMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCompanyMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddCliente.setOnClickListener { findNavController().navigate(R.id.action_companyMenu_to_registerNewCustomerFragment) }
        binding.btnAddSelos.setOnClickListener { findNavController().navigate(R.id.action_companyMenu_to_addCustomerLabelFragment) }
        binding.ivProfile.setOnClickListener { findNavController().navigate(R.id.action_companyMenu_to_companyProfile) }
        binding.btnGerenciarRecompensas.setOnClickListener { findNavController().navigate(R.id.action_companyMenu_to_rewardManagementFragment) }
        binding.btnVisualizarFidelidades.setOnClickListener { findNavController().navigate(R.id.action_companyMenu_to_viewLoyaltyFragment) }


        binding.btnSair.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
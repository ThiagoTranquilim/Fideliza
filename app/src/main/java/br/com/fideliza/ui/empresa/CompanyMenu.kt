package br.com.fideliza.ui.empresa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.fideliza.R
import br.com.fideliza.databinding.FragmentCompanyMenuBinding
import br.com.fideliza.servidor.ConexaoServidor
import br.com.fideliza.servidor.ServerCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.bson.Document

class CompanyMenu : Fragment(), ServerCallback {

    private var _binding: FragmentCompanyMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCompanyMenuBinding.inflate(inflater, container, false)
        firebaseAuth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obter o UID do Firebase para buscar o documento da empresa
        val firebaseUID = firebaseAuth.uid.toString()
        val queryDocument = Document().append("firebaseUID", firebaseUID)
        Log.i("CompanyMenu", "Firebase UID: $firebaseUID")

        //Iniciar a conexão com o servidor passando o filtro e a interface de callback
        ConexaoServidor.conexao("3;${queryDocument.toJson()};empresas", this)

        // Configurações de clique para os botões de navegação
        setupNavigationButtons()
    }

    private fun setupNavigationButtons() {
        binding.btnAddCliente.setOnClickListener { findNavController().navigate(R.id.action_companyMenu_to_registerNewCustomerFragment) }
        binding.btnAddSelos.setOnClickListener { findNavController().navigate(R.id.action_companyMenu_to_addCustomerLabelFragment) }
        binding.ivProfile.setOnClickListener { findNavController().navigate(R.id.action_companyMenu_to_companyProfile) }
        binding.btnGerenciarRecompensas.setOnClickListener { findNavController().navigate(R.id.action_companyMenu_to_rewardManagementFragment) }
        binding.btnVisualizarFidelidades.setOnClickListener { findNavController().navigate(R.id.action_companyMenu_to_viewLoyaltyFragment) }
        binding.btnSair.setOnClickListener { parentFragmentManager.popBackStack() }
    }


    // Callback para tratar o resultado da consulta do servidor
    override fun onResult(resultado: String) {
        if (resultado == "nenhum") {
            Log.i("CompanyMenu", "Nenhum documento encontrado.")
            activity?.runOnUiThread {
                binding.tvEmpresa.text = "Nenhum documento encontrado"
                binding.tvEstabelecimento.text = "Nenhum estabelecimento encontrado"
            }
        } else {
            try {
                val document = Document.parse(resultado)

                // Extrair e exibir informações específicas da empresa na interface
                val nomeEmpresa = document.getString("nome") ?: "Nome não disponível"
                val cnpjEmpresa = document.getString("cnpj") ?: "CNPJ não disponível"

                // Exibir os dados nos TextViews correspondentes
                activity?.runOnUiThread {
                    binding.tvEmpresa.text = nomeEmpresa
                    binding.tvEstabelecimento.text = cnpjEmpresa
                }

            } catch (e: Exception) {
                Log.e("CompanyMenu", "Erro ao processar o resultado: ${e.message}")
                activity?.runOnUiThread {
                    binding.tvEmpresa.text = "Erro ao carregar dados da empresa"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
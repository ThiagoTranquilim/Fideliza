package br.com.fideliza.ui.empresa

import android.os.Bundle
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
    private var ret : Document? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCompanyMenuBinding.inflate(inflater, container, false)
        firebaseAuth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ax = Document().append("firebaseUID", firebaseAuth.uid)

        // Iniciar a conexão com o servidor passando o filtro e a interface de callback
        ConexaoServidor.conexao("3;${ax.toJson()};empresas", this)

        while(ret == null) {}
        binding.tvEmpresa.text = ret!!.get("nome").toString()

        // Configurações de clique para os botões, agora a UI só é atualizada no callback onResult
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

    // Método callback que recebe o resultado do servidor
    override fun onResult(resultado: String?) {

        try {
            ret = Document.parse(resultado)
        }catch (e : Exception) {
            ret = Document.parse("{ \"nome\" : \"erro para obter nome\" }")
        }

    }
}
package br.com.fideliza.ui.empresa

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fideliza.R
import br.com.fideliza.data.Cliente
import br.com.fideliza.data.ClienteAdapter
import br.com.fideliza.databinding.FragmentAddCustomerLabelBinding

class AddCustomerLabelFragment : Fragment(R.layout.fragment_add_customer_label) {

    private var _binding: FragmentAddCustomerLabelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCustomerLabelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar o RecyclerView
        val recyclerView = binding.recyclerViewClientes
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Lista de clientes
        val clientes = listOf(
            Cliente("João Silva"),
            Cliente("Maria Oliveira"),
            Cliente("Carlos Santos"),
            Cliente("Ana Paula")
        )

        // Adapter para exibir os clientes
        val adapter = ClienteAdapter(clientes) { cliente ->
            // Navega para o AddLabelCustomerFragment, passando o nome do cliente
            val action =
                br.com.fideliza.ui.empresa.AddCustomerLabelFragmentDirections.actionAddCustomerLabelFragmentToAddLabelCustomer(
                    cliente.nome
                )
            findNavController().navigate(action)
        }

        // Atribuir o Adapter ao RecyclerView
        recyclerView.adapter = adapter

        // Botão Voltar
        binding.btnVoltar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


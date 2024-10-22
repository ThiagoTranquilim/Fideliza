package br.com.fideliza

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import br.com.fideliza.R
import br.com.fideliza.data.Empresa
import br.com.fideliza.data.EmpresaAdapter
import br.com.fideliza.databinding.FragmentFidelityCardsBinding

class FidelityCardsFragment : Fragment(R.layout.fragment_fidelity_cards) {

    private var _binding: FragmentFidelityCardsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFidelityCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar o RecyclerView
        val recyclerView = binding.recyclerViewCards
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



        // Lista de empresas (Exemplo de dados)
        val empresas = listOf(
            Empresa(R.drawable.baseline_account_circle_black_24, "Empresa 1", "Descrição da Empresa 1"),
            Empresa(R.drawable.baseline_account_circle_black_24, "Empresa 2", "Descrição da Empresa 2"),
            Empresa(R.drawable.baseline_account_circle_black_24, "Empresa 3", "Descrição da Empresa 3")
        )

        // Adapter para exibir os cards das empresas
        val adapter = EmpresaAdapter(empresas) { empresa ->
        }

        // Atribuir o Adapter ao RecyclerView
        recyclerView.adapter = adapter

        // Botão de volta (caso seja necessário)
        binding.btnVoltar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

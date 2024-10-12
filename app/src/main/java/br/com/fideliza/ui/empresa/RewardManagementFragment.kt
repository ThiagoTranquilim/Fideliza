package br.com.fideliza.ui.empresa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.fideliza.R

class RewardManagementFragment : Fragment(R.layout.fragment_reward_management) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referências para os campos de entrada
        val etSelos = view.findViewById<EditText>(R.id.etSelos)
        val etValidade = view.findViewById<EditText>(R.id.etValidade)
        val etRecompensa = view.findViewById<EditText>(R.id.etRecompensa)
        val btnSalvarMecanica = view.findViewById<Button>(R.id.btnSalvarMecanica)

        // Ação ao clicar no botão "Salvar Mecânica"
        btnSalvarMecanica.setOnClickListener {
            val selos = etSelos.text.toString()
            val validade = etValidade.text.toString()
            val recompensa = etRecompensa.text.toString()

            // Validação básica de campos
            if (selos.isNotEmpty() && validade.isNotEmpty() && recompensa.isNotEmpty()) {
                // Aqui você pode adicionar a lógica para salvar a mecânica
                Toast.makeText(requireContext(), "Mecânica salva com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

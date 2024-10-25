package br.com.fideliza.ui.empresa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.fideliza.R

class AddLabelCustomer : Fragment(R.layout.fragment_add_label_customer) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    // Obter referências para os elementos do layout
        val btnLancarSelo = view.findViewById<Button>(R.id.btnLancarSelo)
        val btnVoltar = view.findViewById<TextView>(R.id.btnVoltar)
        val etValor = view.findViewById<EditText>(R.id.etValor)
        val etData = view.findViewById<EditText>(R.id.etData)
        val etDescricao = view.findViewById<EditText>(R.id.etDescricao)

        // Exemplo de ação ao clicar no botão "Lançar Selo"
        btnLancarSelo.setOnClickListener {
            val valor = etValor.text.toString()
            val data = etData.text.toString()
            val descricao = etDescricao.text.toString()

            // Lógica para lançar selo
            Toast.makeText(requireContext(), "Selo lançado para valor: $valor, data: $data", Toast.LENGTH_SHORT).show()

            // Aqui devemos adicionar a lógica de adicionar o selo para o cliente selecionado

            findNavController().navigate(R.id.action_addLabelCustomerFragment_to_tokenValidationCustomerFragment)
        }

        // Ação ao clicar no botão "Voltar"
        btnVoltar.setOnClickListener {
            // Navegar de volta ao fragmento anterior
            parentFragmentManager.popBackStack()
        }
    }
}
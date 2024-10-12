package br.com.fideliza.ui.empresa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.com.fideliza.R

class TokenValidationCustomerFragment : Fragment(R.layout.fragment_token_validation_customer) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referências aos campos de dígito (EditTexts)
        val etDigit1 = view.findViewById<EditText>(R.id.et1)
        val etDigit2 = view.findViewById<EditText>(R.id.et2)
        val etDigit3 = view.findViewById<EditText>(R.id.et3)
        val etDigit4 = view.findViewById<EditText>(R.id.et4)
        val etDigit5 = view.findViewById<EditText>(R.id.et5)
        val etDigit6 = view.findViewById<EditText>(R.id.et6)

        val btnVerificar = view.findViewById<Button>(R.id.btnVerificar)
        val btnVoltar = view.findViewById<TextView>(R.id.btnVoltar)

        // Ação ao clicar no botão "Verificar"
        btnVerificar.setOnClickListener {
            // Concatena os valores dos 6 campos para formar o token
            val token = "${etDigit1.text}${etDigit2.text}${etDigit3.text}${etDigit4.text}${etDigit5.text}${etDigit6.text}"

            if (token.length == 6) {
                // Verificação do token (aqui você pode adicionar sua lógica)
                Toast.makeText(requireContext(), "Token válido: $token", Toast.LENGTH_SHORT).show()
                // Adicionar lógica de validação do token aqui
            } else {
                Toast.makeText(requireContext(), "Token inválido", Toast.LENGTH_SHORT).show()
            }
        }

        // Ação ao clicar no botão "Voltar"
        btnVoltar.setOnClickListener {
            // Navega de volta ao fragmento anterior
            parentFragmentManager.popBackStack()
        }
    }
}

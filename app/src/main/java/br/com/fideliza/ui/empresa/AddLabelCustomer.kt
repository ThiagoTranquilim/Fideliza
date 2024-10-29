package br.com.fideliza.ui.empresa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.fideliza.R
import br.com.fideliza.databinding.FragmentAddLabelCustomerBinding
import br.com.fideliza.servidor.ConexaoServidor
import br.com.fideliza.servidor.ServerCallback
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import org.bson.Document

class AddLabelCustomer : Fragment(R.layout.fragment_add_label_customer), ServerCallback {

    private var _binding: FragmentAddLabelCustomerBinding? = null
    private val binding get() = _binding!!
    private lateinit var ret : Document
    private lateinit var firebaseAuth: FirebaseAuth

    // Utilizando Safe Args para receber o argumento CPF
    private val args: AddLabelCustomerArgs by navArgs()
    private lateinit var cpf: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recuperando o CPF passado no argumento
        cpf = args.cpf

        Log.i("CPF",cpf)
        firebaseAuth = Firebase.auth

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

            val doc = Document()
            doc.append("valor", valor)
                .append("descricao", descricao)

            ConexaoServidor.conexao("2;${firebaseAuth.uid};${cpf};${doc.toJson()}", this)

            // Aqui devemos adicionar a lógica de adicionar o selo para o cliente selecionado
            findNavController().navigate(R.id.action_addLabelCustomerFragment_to_tokenValidationCustomerFragment)
        }

        // Ação ao clicar no botão "Voltar"
        btnVoltar.setOnClickListener {
            // Navegar de volta ao fragmento anterior
            parentFragmentManager.popBackStack()
        }
    }

    override fun onResult(resposta: String) {
        try {
            Log.i("Sucesso", resposta)
            // Lógica para lançar selo
            Toast.makeText(requireContext(), "Selo lançado com sucesso", Toast.LENGTH_SHORT).show()
        } catch (a: Exception) {
            Log.e("ERRS", a.message.toString())
        }
    }
}
package br.com.fideliza.ui.empresa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import br.com.fideliza.R
import br.com.fideliza.databinding.FragmentAddLabelCustomerBinding
import br.com.fideliza.servidor.ConexaoServidor
import br.com.fideliza.servidor.ServerCallback
import com.google.firebase.auth.FirebaseAuth
import org.bson.Document

class AddLabelCustomer : Fragment(R.layout.fragment_add_label_customer), ServerCallback {

    private var _binding: FragmentAddLabelCustomerBinding? = null
    private val binding get() = _binding!!
    private lateinit var ret: Document
    private lateinit var firebaseAuth: FirebaseAuth

    // Utilizando Safe Args para receber o argumento CPF
    private val args: AddLabelCustomerArgs by navArgs()
    private lateinit var cpf: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddLabelCustomerBinding.bind(view)  // Inicialização do binding

        // Recuperando o CPF passado no argumento
        cpf = args.cpf
        Log.i("CPF", cpf)

        firebaseAuth = FirebaseAuth.getInstance()

        // Obter referências para os elementos do layout
        val btnLancarSelo = binding.btnLancarSelo
        val btnVoltar = binding.btnVoltar
        val etValor = binding.etValor
        val etDescricao = binding.etDescricao

        ConexaoServidor.conexao("3;{ \"cpf\" : \"${cpf}\" };clientes", this)

        // Ação ao clicar no botão "Lançar Selo"
        btnLancarSelo.setOnClickListener {
            val valor = etValor.text.toString()
            val descricao = etDescricao.text.toString()

            if (valor.isEmpty() || descricao.isEmpty()) {
                Toast.makeText(requireContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Exibir um AlertDialog para confirmação
            val builder = android.app.AlertDialog.Builder(requireContext())
            builder.setTitle("Confirmação")
            builder.setMessage("Deseja realmente lançar o selo com os dados informados?\n\nValor: $valor\nDescrição: $descricao")

            val result = android.app.AlertDialog.Builder(requireContext())

            // Botão "Sim"
            builder.setPositiveButton("Sim") { _, _ ->
                val doc = Document()
                doc.append("valor", valor).append("descricao", descricao)

                // Chamadas para o servidor
                ConexaoServidor.conexao("2;${firebaseAuth.uid};${cpf};${doc.toJson()}", this@AddLabelCustomer)

                // Exibir uma mensagem de sucesso
                Toast.makeText(requireContext(), "Selo lançado com sucesso", Toast.LENGTH_SHORT).show()

                // Chama o método clearForm para limpar os campos
                clearForm()

                // Perguntar se deseja lançar outro selo
                result.setTitle("Selo registrado")
                result.setMessage("Deseja lançar outro selo?")
                result.setPositiveButton("Sim") { _, _ ->
                    // Limpar novamente os campos para um novo selo
                    clearForm()
                }
                result.setNegativeButton("Não") { dialog, _ ->
                    dialog.dismiss()
                    parentFragmentManager.popBackStack()
                }
                result.create().show()
            }

            // Botão "Não"
            builder.setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss()
            }

            // Exibir o diálogo
            builder.create().show()
        }


        // Ação ao clicar no botão "Voltar"
        btnVoltar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onResult(resposta: String) {
        Log.i("Resp", resposta)
        try {
            if (resposta == "Sucesso") {
                Toast.makeText(requireContext(), "Selo lançado com sucesso", Toast.LENGTH_SHORT).show()
            } else {
                ret = Document.parse(resposta)
                Log.i("ParsedDocument", ret.toJson())

                // Verifique se o fragmento está adicionado antes de atualizar o binding
                if (isAdded) {
                    val nomeCliente = ret.getString("nome") ?: "Nome não encontrado"
                    Log.i("NomeCliente", nomeCliente) // Verificação do nome
                    binding.tvNomeCliente.text = nomeCliente
                } else {
                    Log.e("ERRS", "Fragment não está mais anexado à Activity")
                }
            }
        } catch (e: Exception) {
            Log.e("ERRS", e.message.toString())
        }
    }
    private fun clearForm() {
        // Limpar os campos de texto
        binding.etValor.text!!.clear()
        binding.etDescricao.text!!.clear()
        // Caso tenha outros campos, você pode adicionar aqui, por exemplo:
        // binding.etData.text.clear()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
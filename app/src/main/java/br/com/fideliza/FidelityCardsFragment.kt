package br.com.fideliza

import Empresa
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fideliza.data.EmpresaAdapter
import br.com.fideliza.servidor.ConexaoServidor
import com.google.gson.Gson
import br.com.fideliza.R

class FidelityCardsFragment : Fragment() {
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fidelity_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar o RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewCards)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        // Iniciar a Thread para buscar os dados do servidor
        ConexaoServidor.conexao(
            "4;12rLevnKxrT58WCyAQ4TNkl4e5N2"
        ) { respostaDoServidor ->
            // Parse da resposta JSON
            val empresas: List<Empresa> = parseJsonResponse(respostaDoServidor)

            // Atualiza a UI na thread principal usando runOnUiThread
            activity?.runOnUiThread {
                recyclerView?.let { rv ->
                    val adapter = EmpresaAdapter(
                        empresas,
                        onItemClick = { empresa ->
                            // Implementação da lógica de clique, se necessário
                        }
                    )
                    rv.adapter = adapter
                }
            }
        }
    }

    private fun parseJsonResponse(response: String): List<Empresa> {
        val gson = Gson()
        val empresas = mutableListOf<Empresa>()

        // Separando os objetos JSON com o delimitador `;`
        val jsonArray = response.split(";").filter { it.isNotEmpty() }

        for (jsonString in jsonArray) {
            try {
                // Log do JSON bruto
                Log.d("ParseJsonResponse", "JSON bruto: $jsonString")

                // Cria uma instância de Empresa a partir do JSON usando Gson
                val empresa = gson.fromJson(jsonString, Empresa::class.java)

                // Verifica se a conversão foi bem-sucedida
                if (empresa != null) {
                    Log.d("ParseJsonResponse", "Empresa convertida: ${empresa.empresa}, Descrição: ${empresa.descricao}")
                    empresa.logo = R.drawable.baseline_account_circle_black_24 // Define a logo padrão
                    empresas.add(empresa)
                } else {
                    Log.e("ParseJsonResponse", "Erro ao converter JSON: $jsonString")
                }
            } catch (e: Exception) {
                e.printStackTrace() // Trata erro de parsing se necessário
                Log.e("ParseJsonResponse", "Exception ao converter JSON: ${e.message}")
            }
        }

        return empresas
    }
}
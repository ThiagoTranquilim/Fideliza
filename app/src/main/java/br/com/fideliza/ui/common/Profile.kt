package br.com.fideliza.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fideliza.R
import br.com.fideliza.servidor.ConexaoServidor
import br.com.fideliza.servidor.ServerCallback
import com.google.firebase.auth.FirebaseAuth
import org.bson.Document

class Profile : Fragment(), ServerCallback {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private var documentList: MutableList<Document> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewProfile)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        view.findViewById<View>(R.id.btnVoltar).setOnClickListener {
            val action = ProfileDirections.actionProfilePop()
            findNavController().navigate(action)
        }

        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUID = firebaseAuth.uid ?: return

        buscarDocumento("clientes")
    }

    private fun buscarDocumento(colecao: String) {
        val doc = Document().append("firebaseUID", firebaseAuth.uid)
        ConexaoServidor.conexao("3;${doc.toJson()};$colecao", this)
    }

    override fun onResult(resultado: String) {
        if (resultado == "nenhum documento encontrado.") {
            buscarDocumento("empresas")
        } else {
            try {
                val doc = Document.parse(resultado)
                documentList.add(doc) // Adiciona o documento à lista
                activity?.runOnUiThread {
                    recyclerView.adapter = ProfileAdapter(documentList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                exibirMensagemErro("Erro ao processar o documento")
            }
        }
    }

    private fun exibirMensagemErro(mensagem: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), mensagem, Toast.LENGTH_LONG).show()
        }
    }
}

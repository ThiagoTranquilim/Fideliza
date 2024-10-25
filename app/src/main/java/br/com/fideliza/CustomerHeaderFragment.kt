package br.com.fideliza

import Cliente
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import br.com.fideliza.servidor.ConexaoServidor
import br.com.fideliza.servidor.ServerCallback
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.gson.Gson
import org.bson.Document;

class CustomerHeaderFragment : Fragment(R.layout.fragment_customer_header), ServerCallback {

    private var ret : Document? = null;


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var firebaseAuth : FirebaseAuth
        firebaseAuth = Firebase.auth

        var ax = org.bson.Document("firebaseUID", firebaseAuth.uid.toString())
        ConexaoServidor.conexao("3;${ax.toJson()};clientes",this)

        while(ret == null) {}

        var tvNome = view.findViewById<TextView>(R.id.tvClientName)
        var tvCPF = view.findViewById<TextView>(R.id.tvClientCPF)

        tvNome.text = ret!!.get("nome").toString();
        tvCPF.text = ret!!.get("cpf").toString();

        // Encontrar os botões
        val btnFidelityCards = view.findViewById<Button>(R.id.btnFidelityCards)
        val btnRewards = view.findViewById<Button>(R.id.btnRewards)

        // Configurar o clique para trocar o fragmento de conteúdo para o FidelityCardsFragment
        btnFidelityCards.setOnClickListener {
            // Verificar se o fragmento pai é o CustomerMenuFragment e substituir o fragmento de conteúdo
            (parentFragment as? CustomerMenuFragment)?.replaceContentFragment(FidelityCardsFragment())
        }

        // Configurar o clique para trocar o fragmento de conteúdo para o RewardsFragment
        btnRewards.setOnClickListener {
            (parentFragment as? CustomerMenuFragment)?.replaceContentFragment(RewardsFragment())
        }
    }

    override fun onResult(resultado: String) {

        ret = Document.parse(resultado)
    }
}

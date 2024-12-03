package br.com.fideliza.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.fideliza.R
import com.google.firebase.auth.FirebaseAuth
import org.bson.Document

class ProfileAdapter(private val list: List<Document>) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val doc = list[position]

        // Substituir os valores dinâmicos diretamente nos TextViews corretos
        holder.tvNomeValor.text = doc.getString("nome") ?: "N/A"
        holder.tvEmailValor.text = doc.getString("email") ?: "N/A"
        holder.tvDocumentoValor.text = if (doc.containsKey("cpf")) {
            doc.getString("cpf") ?: "N/A"
        } else {
            doc.getString("cnpj") ?: "N/A"
        }

        holder.btnLogout.setOnClickListener { view ->
            val firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()

            try {
                // Verifica o estado atual do NavController
                val navController = view.findNavController()
                if (navController.currentDestination?.id == R.id.Profile) {
                    navController.navigate(R.id.action_Profile_to_loginFragment)
                } else {
                    Toast.makeText(view.context, "Erro: Destino atual inválido para navegação.", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(view.context, "Erro ao navegar: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNomeValor: TextView = itemView.findViewById(R.id.tvNomeValor)
        val tvEmailValor: TextView = itemView.findViewById(R.id.tvEmailValor)
        val tvDocumentoValor: TextView = itemView.findViewById(R.id.tvDocumentoValor)
        val btnLogout: Button = itemView.findViewById(R.id.btnLogout) // Referência ao botão de logout
    }
}
package br.com.fideliza.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fideliza.R
import org.bson.Document

class ProfileAdapter(private val list: List<Document>) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val doc = list[position]
        holder.tvNome.text = doc.getString("nome")
        holder.tvEmail.text = doc.getString("email")
        holder.tvDocumento.text = if (doc.containsKey("cpf")) "CPF: ${doc.getString("cpf")}" else "CNPJ: ${doc.getString("cnpj")}"
    }

    override fun getItemCount(): Int = list.size

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNome: TextView = itemView.findViewById(R.id.tvNome)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val tvDocumento: TextView = itemView.findViewById(R.id.tvDocumento)
    }
}

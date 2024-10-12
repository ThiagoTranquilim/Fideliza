package br.com.fideliza.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fideliza.R

data class Company(val logo: Int, val name: String, val description: String)

class CompanyAdapter(private val companies: List<Company>) : RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    inner class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyLogo: ImageView = itemView.findViewById(R.id.ivCompanyLogo)
        val companyName: TextView = itemView.findViewById(R.id.tvCompanyName)
        val companyDescription: TextView = itemView.findViewById(R.id.tvCompanyDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_company_card, parent, false)
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company = companies[position]
        holder.companyLogo.setImageResource(company.logo) // Usando uma imagem drawable
        holder.companyName.text = company.name
        holder.companyDescription.text = company.description
    }

    override fun getItemCount(): Int = companies.size
}
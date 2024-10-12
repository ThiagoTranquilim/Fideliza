package br.com.fideliza.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fideliza.R
import br.com.fideliza.data.Empresa

class EmpresaAdapter(private val empresas: List<Empresa>, private val onItemClick: (Empresa) -> Unit) :
    RecyclerView.Adapter<EmpresaAdapter.CompanyViewHolder>() {

    inner class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyLogo: ImageView = itemView.findViewById(R.id.ivCompanyLogo)
        val companyName: TextView = itemView.findViewById(R.id.tvCompanyName)
        val companyDescription: TextView = itemView.findViewById(R.id.tvCompanyDescription)

        fun bind(empresa: Empresa) {
            companyLogo.setImageResource(empresa.logo)
            companyName.text = empresa.name
            companyDescription.text = empresa.description

            itemView.setOnClickListener {
                onItemClick(empresa)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_company_card, parent, false)
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val empresa = empresas[position]
        holder.bind(empresa)
    }

    override fun getItemCount(): Int = empresas.size
}

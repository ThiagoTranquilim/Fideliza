package br.com.fideliza.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import br.com.fideliza.R

class ClienteAdapter(
    private val clientes: List<Cliente>,
    private val onClienteSelected: (Cliente) -> Unit
) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    class ClienteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeCliente: TextView = view.findViewById(R.id.tvClienteNome)
        val btnSelecionar: AppCompatButton = view.findViewById(R.id.btnSelecionar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.nomeCliente.text = cliente.nome
        holder.btnSelecionar.setOnClickListener {
            onClienteSelected(cliente) // Ação ao clicar em "Selecionar"
        }
    }

    override fun getItemCount(): Int = clientes.size
}


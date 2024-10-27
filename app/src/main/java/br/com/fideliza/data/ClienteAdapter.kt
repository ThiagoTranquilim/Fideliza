import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.findNavController
import br.com.fideliza.R
import br.com.fideliza.ui.empresa.AddCustomerLabelFragmentDirections

class ClienteAdapter(
    private val clientes: List<Cliente>,
    private val onAddPointClick: (Cliente) -> Unit
) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.bind(cliente)
    }

    override fun getItemCount() = clientes.size

    inner class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNome: TextView = itemView.findViewById(R.id.tvNome)
        private val btnAdicionarPonto: Button = itemView.findViewById(R.id.btnAdicionarPonto)

        fun bind(cliente: Cliente) {
            tvNome.text = cliente.nome

            // Clique no botão "Adicionar Ponto"
            btnAdicionarPonto.setOnClickListener {
                // Usando Safe Args para navegar passando o CPF
                val action = AddCustomerLabelFragmentDirections
                    .actionAddCustomerLabelFragmentToAddLabelCustomer(cliente.cpf)
                itemView.findNavController().navigate(action)
            }
        }
    }
}
package br.com.fideliza.ui.empresa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.gridlayout.widget.GridLayout
import br.com.fideliza.R

class ViewLoyaltyFragment : Fragment(R.layout.fragment_view_loyalty) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referência ao GridLayout
        val gridLayout = view.findViewById<GridLayout>(R.id.gridLayoutFidelidades)
        val btnVoltar = view.findViewById<TextView>(R.id.btnVoltar)

        // Lista de imagens de exemplo para as fidelidades
        val fidelidades = listOf(
            R.drawable.ic_placeholder_image,
            R.drawable.ic_placeholder_image,
            R.drawable.ic_placeholder_image,
            R.drawable.ic_placeholder_image,
            R.drawable.ic_placeholder_image,
            R.drawable.ic_placeholder_image,
            R.drawable.ic_placeholder_image,
            R.drawable.ic_placeholder_image,
            R.drawable.ic_placeholder_image
        )

        // Preencher o GridLayout dinamicamente
        for (fidelidade in fidelidades) {
            // Infla o layout do item fidelidade
            val itemView = layoutInflater.inflate(R.layout.item_fidelidade, gridLayout, false)

            // Atualiza a imagem no item inflado
            val imageView = itemView.findViewById<ImageView>(R.id.imageViewFidelidade)
            imageView.setImageResource(fidelidade)

            // Adiciona o item ao GridLayout
            gridLayout.addView(itemView)
        }

        // Ação ao clicar no botão "Voltar"
        btnVoltar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}


package br.com.fideliza

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button

class CustomerHeaderFragment : Fragment(R.layout.fragment_customer_header) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}

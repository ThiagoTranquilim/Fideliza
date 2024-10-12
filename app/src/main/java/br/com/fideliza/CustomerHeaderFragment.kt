package br.com.fideliza

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class CustomerHeaderFragment : Fragment(R.layout.fragment_customer_header) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnFidelityCards = view.findViewById<Button>(R.id.btnFidelityCards)
        val btnRewards = view.findViewById<Button>(R.id.btnRewards)

        btnFidelityCards.setOnClickListener {
            (parentFragment as? CustomerMenuFragment)?.replaceContentFragment(FidelityCardsFragment())
        }

        btnRewards.setOnClickListener {
            (parentFragment as? CustomerMenuFragment)?.replaceContentFragment(RewardsFragment())
        }
    }
}

package br.com.fideliza

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CustomerMenuFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Carregar o fragmento da parte azul
        childFragmentManager.beginTransaction()
            .replace(R.id.headerFragmentContainer, CustomerHeaderFragment())
            .commit()

        // Carregar o fragmento da parte cinza (padrão)
        childFragmentManager.beginTransaction()
            .replace(R.id.contentFragmentContainer, CustomerContentFragment())
            .commit()
    }

    fun replaceContentFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.contentFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
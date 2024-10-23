package br.com.fideliza

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController

class CustomerMenuFragment : Fragment(R.layout.fragment_customer_menu) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Carregar o fragmento da parte azul (CustomerHeaderFragment)
        childFragmentManager.beginTransaction()
            .replace(R.id.headerFragmentContainer, CustomerHeaderFragment())
            .commit()

        // Carregar o fragmento da parte cinza (CustomerContentFragment)
        childFragmentManager.beginTransaction()
            .replace(R.id.contentFragmentContainer, CustomerContentFragment())
            .commit()
    }

    fun navigateToProfile() {
        // Usar o NavController do Menu para navegação
        findNavController().navigate(R.id.action_customerMenuFragment_to_Profile)
    }

    fun replaceContentFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.contentFragmentContainer, fragment)
            .addToBackStack(null) // Isso garante que a navegação seja empilhada
            .commit()
    }
}

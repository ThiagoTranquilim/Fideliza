package br.com.fideliza

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController

class CustomerContentFragment : Fragment(R.layout.fragment_customer_content) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val btnProfile = view.findViewById<Button>(R.id.btnProfile)
/*
        btnProfile.setOnClickListener {
            // Navegar para o Profile usando o método do fragmento pai
            (parentFragment as? CustomerMenuFragment)?.navigateToProfile()
        }*/
    }
}

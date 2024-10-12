package br.com.fideliza

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class CustomerContentFragment : Fragment(R.layout.fragment_customer_content) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnProfile = view.findViewById<Button>(R.id.btnProfile)

        btnProfile.setOnClickListener {
            // Ação ao clicar em "Meu Perfil"
        }
    }
}

package br.com.fideliza

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import br.com.fideliza.R
import br.com.fideliza.firebase.auth.FirebaseAuthManager

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var firebaseAuthManager : FirebaseAuthManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        firebaseAuthManager = FirebaseAuthManager()

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun getFirebaseAuth() : FirebaseAuthManager {
        return firebaseAuthManager
    }
}
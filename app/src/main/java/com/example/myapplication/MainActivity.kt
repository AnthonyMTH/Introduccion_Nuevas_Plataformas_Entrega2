package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.fragments.EdificiosFragment
import com.example.myapplication.fragments.InicioFragment
import com.example.myapplication.fragments.MapaFragment
import com.example.myapplication.fragments.PerfilFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            val selectedFragment: Fragment = when (menuItem.itemId) {
                R.id.navigation_inicio -> InicioFragment()
                R.id.navigation_edificios -> EdificiosFragment()
                R.id.navigation_mapa -> MapaFragment()
                R.id.navigation_perfil -> PerfilFragment()
                else -> InicioFragment()
            }
            openFragment(selectedFragment)
            true
        }

        // Selecciona el fragmento de inicio al iniciar la app
        bottomNavigationView.selectedItemId = R.id.navigation_inicio
    }

    fun navigateToFragment(fragment: Fragment, menuItemId: Int) {
        openFragment(fragment)
        bottomNavigationView.selectedItemId = menuItemId
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}
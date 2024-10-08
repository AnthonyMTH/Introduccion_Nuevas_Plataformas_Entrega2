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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            var selectedFragment: Fragment? = null
            when (menuItem.itemId) {
                R.id.navigation_inicio -> selectedFragment = InicioFragment()
                R.id.navigation_edificios -> selectedFragment = EdificiosFragment()
                R.id.navigation_mapa -> selectedFragment = MapaFragment()
                R.id.navigation_perfil -> selectedFragment = PerfilFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit()
            }
            true
        }

        // Set default fragment
        bottomNavigationView.selectedItemId = R.id.navigation_inicio
    }
}
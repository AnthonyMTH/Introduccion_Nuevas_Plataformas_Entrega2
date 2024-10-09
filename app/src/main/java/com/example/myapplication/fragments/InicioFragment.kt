package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.MainActivity
import com.example.myapplication.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InicioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InicioFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        val btnExplorarEdificaciones: Button = view.findViewById(R.id.btnExplorarEdificaciones)
        val btnIrAlMapa: Button = view.findViewById(R.id.btnIrAlMapa)
        val tvSaludo: TextView = view.findViewById(R.id.tvSaludo)

        // Mensaje de saludo personalizado
        tvSaludo.text = "¡Hola Gabriel!"

        // Acción para el botón de explorar edificaciones
        btnExplorarEdificaciones.setOnClickListener {
            (activity as MainActivity).navigateToFragment(EdificiosFragment(), R.id.navigation_edificios)
        }

        // Acción para el botón de ir al mapa
        btnIrAlMapa.setOnClickListener {
            (activity as MainActivity).navigateToFragment(MapaFragment(), R.id.navigation_mapa)
        }

        return view
    }
}
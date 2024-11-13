package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.views.EdificioView
import com.example.myapplication.viewmodels.EdificioViewModel

class EdificioMapFragment : Fragment() {

    private lateinit var viewModel: EdificioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edificio_map_fragment, container, false)
        // Inicializar el ViewModel
        viewModel = ViewModelProvider(this)[EdificioViewModel::class.java]

        // Llamar al método para cargar los datos desde el CSV
        viewModel.cargarDatosDesdeCSV(requireContext(), "ambientes.csv")

        // Crear la vista personalizada
        val edificioView = view.findViewById<EdificioView>(R.id.edificioView)
        val btnVolver = view.findViewById<Button>(R.id.btn_volver)

        // Configurar el botón para volver al fragmento anterior
        btnVolver.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        // Observar los datos y actualizar la vista
        viewModel.ambientes.observe(viewLifecycleOwner) { ambientes ->
            edificioView.setAmbientes(ambientes)
        }

        return view
    }
}
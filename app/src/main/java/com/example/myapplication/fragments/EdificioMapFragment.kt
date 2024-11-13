package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.views.EdificioView
import com.example.myapplication.viewmodels.EdificioViewModel

class EdificioMapFragment : Fragment() {

    private lateinit var viewModel: EdificioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inicializar el ViewModel
        viewModel = ViewModelProvider(this)[EdificioViewModel::class.java]

        // Llamar al método para cargar los datos desde el CSV
        viewModel.cargarDatosDesdeCSV(requireContext(), "ambientes.csv")

        // Crear la vista personalizada
        val edificioView = EdificioView(context)

        // Observar los datos y actualizar la vista
        viewModel.ambientes.observe(viewLifecycleOwner) { ambientes ->
            edificioView.setAmbientes(ambientes)
        }

        return edificioView
    }
}
package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.views.EdificioView
import com.example.myapplication.viewmodels.EdificioViewModel

class EdificioMapFragment : Fragment() {

    companion object {
        private const val ARG_NAME = "ARG_NAME"

        fun newInstance(name: String): EdificioMapFragment {
            val fragment = EdificioMapFragment()
            val args = Bundle().apply {
                putString(ARG_NAME, name)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private var name: String? = null
    private lateinit var mensajeTextView: TextView
    private lateinit var viewModel: EdificioViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            name = it.getString(ARG_NAME)
        }
        val view = inflater.inflate(R.layout.edificio_map_fragment, container, false)

        mensajeTextView = view.findViewById<TextView>(R.id.tv_no_map)
        val tituloTextView = view.findViewById<TextView>(R.id.edificacionTitle)
        tituloTextView.text = name


        val nombre = name ?: ""
        if (verificarExistenciaDeArchivo(nombre)) {
            // Inicializar el ViewModel
            viewModel = ViewModelProvider(this)[EdificioViewModel::class.java]

            val nombreArchivo = nombre.toString()+".csv"
            // Llamar al método para cargar los datos desde el archivo CSV
            viewModel.cargarDatosDesdeCSV(requireContext(), nombreArchivo)

            // Crear la vista personalizada
            val edificioView = view?.findViewById<EdificioView>(R.id.edificioView)

            // Observar los datos y actualizar la vista
            viewModel.ambientes.observe(viewLifecycleOwner) { ambientes ->
                edificioView?.setAmbientes(ambientes)
            }
        } else {
            mensajeTextView.text = "No se ha cargado un plano para esta edificación"
        }

        val btnVolver = view.findViewById<Button>(R.id.btn_volver)
        // Configurar el botón para volver al fragmento anterior
        btnVolver.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        return view
    }


    fun verificarExistenciaDeArchivo(nombreArchivoConAmbientes: String):Boolean{
        val archivoExiste = requireContext().assets.list("")?.contains("$nombreArchivoConAmbientes.csv") == true
        // Si el archivo no existe, devolver false y no hacer nada
        if (!archivoExiste) {
            return false
        }
        return true
    }

    private fun mostrarMensaje() {
        mensajeTextView.visibility = View.VISIBLE
    }

    private fun ocultarMensaje() {
        mensajeTextView.visibility = View.GONE
    }
}
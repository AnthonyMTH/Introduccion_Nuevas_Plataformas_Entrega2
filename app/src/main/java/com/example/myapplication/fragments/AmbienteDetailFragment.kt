package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class AmbienteDetailFragment : Fragment() {

    private var nombreAmbiente: String? = null
    private var descripcionAmbiente: String? = null
    private var imagenAmbiente: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nombreAmbiente = it.getString(ARG_AMBIENTE_NAME)
            descripcionAmbiente = it.getString(ARG_AMBIENTE_DESCRIPTION)
            imagenAmbiente = it.getInt(ARG_AMBIENTE_IMAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ambiente_detail, container, false)

        // Configurar el nombre y la descripción del ambiente
        view.findViewById<TextView>(R.id.text_view_ambiente_name).text = nombreAmbiente ?: "Nombre del Ambiente"
        view.findViewById<TextView>(R.id.text_view_ambiente_description).text = descripcionAmbiente ?: "Descripción no disponible."

        // Configurar una imagen de ejemplo
        view.findViewById<ImageView>(R.id.image_view_ambiente).setImageResource(imagenAmbiente ?: R.drawable.noimagenfound )

        // Botton regresar
        view.findViewById<Button>(R.id.button_regresar).setOnClickListener {
            // cierra el fragment
            activity?.supportFragmentManager?.popBackStack()
        }

        return view
    }

    companion object {
        private const val ARG_AMBIENTE_NAME = "nombreAmbiente"
        private const val ARG_AMBIENTE_DESCRIPTION = "descripcionAmbiente"
        private const val ARG_AMBIENTE_IMAGE = "imagenAmbiente"

        fun newInstance(nombre: String, descripcion: String, image: Int): AmbienteDetailFragment {
            val fragment = AmbienteDetailFragment()
            val args = Bundle()
            args.putString(ARG_AMBIENTE_NAME, nombre)
            args.putString(ARG_AMBIENTE_DESCRIPTION, descripcion)
            args.putInt(ARG_AMBIENTE_IMAGE, image)
            fragment.arguments = args
            return fragment
        }
    }
}

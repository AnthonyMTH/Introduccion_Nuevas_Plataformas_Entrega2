package com.example.myapplication.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.models.Edificio
import com.example.myapplication.services.AudioService

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_EDIFICIO_NAME = "edificio"
private const val ARG_EDIFICIO_DESCRIPTION = "descripcionEdificio"
private const val ARG_EDIFICIO_IMAGE = "imagenEdificio"
private const val ARG_EDIFICIO_SONG_NAME = "songNameEdificio"

/**
 * A simple [Fragment] subclass.
 * Use the [EdificacionDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EdificacionDetailFragment : Fragment() {
    private var name: String? = null
    private var description: String? = null
    private var imageURL: String? = null
    private var songName: String? = null

    private var isPaused = false
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_EDIFICIO_NAME)
            description = it.getString(ARG_EDIFICIO_DESCRIPTION)
            imageURL = it.getString(ARG_EDIFICIO_IMAGE)
            songName = it.getString(ARG_EDIFICIO_SONG_NAME)
        }



    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edificacion_detail, container, false)
        Log.d("namesong","${songName}" )
        val nameTextView = view.findViewById<TextView>(R.id.edificacionTitle)
        val descriptionTextView = view.findViewById<TextView>(R.id.edificacionDescription)
        val imageWidgetView = view.findViewById<ImageView>(R.id.edificacionImage)
        val backButton = view.findViewById<ImageButton>(R.id.backButton)
        val comentarButton = view.findViewById<Button>(R.id.btnComentar)
        val mostrarPlanoButton = view.findViewById<Button>(R.id.btnMostrarPlano)
        val playPause = view.findViewById<ImageButton>(R.id.btnPlayPause)

        nameTextView.text = name
        descriptionTextView.text = description

        backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
            val intent = Intent(requireContext(), AudioService::class.java)
                intent.action = "STOP"
                requireActivity().startService(intent)
                isPlaying = false
                isPaused = false
        }

        comentarButton.setOnClickListener {
            mostrarComentariosEdificacion(name?:"Nombre edificación", imageURL?:"url")
        }

        mostrarPlanoButton.setOnClickListener {
            mostrarPlanoEdificacion()
        }

        playPause.setOnClickListener {
            val intent = Intent(requireContext(), AudioService::class.java)
            when {
                !isPlaying -> {
                    intent.action = "START"
                    intent.putExtra("SONG_NAME", songName)
                    intent.putExtra("EDIFICATION_NAME", name)
                    playPause.setImageResource(R.drawable.pause_icon)
                    isPlaying = true
                    isPaused = false
                }
                isPaused -> {
                    intent.action = "RESUME"
                    playPause.setImageResource(R.drawable.pause_icon)
                    isPaused = false
                }
                else -> {
                    intent.action = "PAUSE"
                    playPause.setImageResource(R.drawable.play_icon)
                    isPaused = true
                }
            }
            requireActivity().startService(intent)
        }


        Glide.with(requireContext()).load(imageURL).into(imageWidgetView)

        return view
    }

    private fun mostrarComentariosEdificacion( name: String, imageURL: String) {

        val fragment = ComentariosEdificacionFragment.newInstance(
            name = name,
            imageURL = imageURL
        )

        val activity = context as? AppCompatActivity
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun mostrarPlanoEdificacion() {
        val nombreEdificacion = name.toString()
        val fragment = EdificioMapFragment.newInstance(nombreEdificacion)

        val activity = context as? AppCompatActivity
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }


    companion object {


        @JvmStatic fun newInstance(name: String, description: String, imageURL: String, songName: String ) =
                EdificacionDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_EDIFICIO_NAME, name)
                        putString(ARG_EDIFICIO_DESCRIPTION, description)
                        putString(ARG_EDIFICIO_IMAGE, imageURL)
                        putString(ARG_EDIFICIO_SONG_NAME, songName)
                    }
                }
    }
}
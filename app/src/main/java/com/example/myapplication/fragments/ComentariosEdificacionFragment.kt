package com.example.myapplication.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.adapters.CommentAdapter
import com.example.myapplication.models.Comment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_EDIFICIO_NAME = "nombreEdificio"
private const val ARG_EDIFICIO_IMAGE = "imagenEdificio"

/**
 * A simple [Fragment] subclass.
 * Use the [ComentariosEdificacionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ComentariosEdificacionFragment : Fragment() {
    private var name: String? = null
    private var imageURL: String? = null
    private val linearLayoutManager = LinearLayoutManager(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_EDIFICIO_NAME)
            imageURL = it.getString(ARG_EDIFICIO_IMAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comentarios_edificacion, container, false)

        val nameTextView = view.findViewById<TextView>(R.id.edificacionTitle)
        val imageWidgetView = view.findViewById<ImageView>(R.id.edificacionImage)
        val backButton = view.findViewById<ImageButton>(R.id.backButton)
        val saveCommentButton = view.findViewById<Button>(R.id.btnEnviarComentario)

        nameTextView.text = name
        backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        Glide.with(requireContext()).load(imageURL).into(imageWidgetView)

        // RecyclerView
        val recyclerViewComments = view.findViewById<RecyclerView>(R.id.comentariosRecyclerView)

        // Ejemplos comentarios
        val comentarios: MutableList<Comment> = mutableListOf(
            Comment(author = "Juan Pérez", comment = "Este es un excelente artículo, muy informativo.", date = "22/12/2004"),
            Comment(author = "Ana Gómez", comment = "No estoy de acuerdo con algunas ideas, pero es un buen punto de partida.", date = "23/12/2004"),
            Comment(author = "Carlos Sánchez", comment = "¿Podrías ampliar la información sobre el tema?", date = "24/12/2004"),
            Comment(author = "Lucía Martínez", comment = "Me ayudó mucho, gracias por compartir.", date = "25/12/2004"),
            Comment(author = "Pedro Rodríguez", comment = "Interesante, aunque faltan algunos detalles técnicos.", date = "26/12/2004")
        )

        saveCommentButton.setOnClickListener {
            val comment = view.findViewById<EditText>(R.id.commentEditText)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val currentDate = dateFormat.format(Date())

            val newComment = Comment(author = "Me", comment = comment.text.toString(), date = currentDate)
            comentarios.add(0,newComment)
            (recyclerViewComments.adapter as? CommentAdapter)?.notifyItemInserted(0)
            comment.text.clear()

            // Ocultar el teclado
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(comment.windowToken, 0)

            linearLayoutManager.scrollToPosition(0)
        }


        recyclerViewComments.layoutManager = linearLayoutManager
        recyclerViewComments.adapter = CommentAdapter(comentarios)


        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(name: String, imageURL: String) =
            ComentariosEdificacionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_EDIFICIO_NAME, name)
                    putString(ARG_EDIFICIO_IMAGE, imageURL)
                }
            }
    }
}
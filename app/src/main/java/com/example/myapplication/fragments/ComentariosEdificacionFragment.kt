package com.example.myapplication.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.myapplication.models.Category
import com.example.myapplication.models.CategoryList
import com.example.myapplication.models.Comment
import com.google.gson.Gson
import java.io.File
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

    //datos de usuario
    private lateinit var userName: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        copyJsonToInternalStorage()
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

        // Inicializa el TextView
        userName = view.findViewById(R.id.userName)
        // Recuperar el nombre del usuario desde SharedPreferences
        val sharedPreferences = activity?.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("username", "Invitado") // Valor por defecto si no existe
        userName.text = username

        nameTextView.text = name
        backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        Glide.with(requireContext()).load(imageURL).into(imageWidgetView)

        // RecyclerView
        val recyclerViewComments = view.findViewById<RecyclerView>(R.id.comentariosRecyclerView)

        //  comentarios desde json
        val comentarios = getCommentsForEdification(name ?: "", requireContext()).toMutableList()




        saveCommentButton.setOnClickListener {
            val commentEditText = view.findViewById<EditText>(R.id.commentEditText)

            if (commentEditText.text.isNotEmpty()) {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val currentDate = dateFormat.format(Date())

                // Crea el nuevo comentario
                val newComment = Comment(author = username.toString(), comment = commentEditText.text.toString(), date = currentDate)

                // Añade el comentario a la lista de comentarios y actualiza el adapter
                comentarios.add(0, newComment)
                (recyclerViewComments.adapter as? CommentAdapter)?.notifyItemInserted(0)

                // Limpia el campo de texto
                commentEditText.text.clear()

                // Oculta el teclado
                val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(commentEditText.windowToken, 0)

                // Guarda los cambios en el archivo JSON
                saveCommentsToJSON(newComment)

                // Desplaza el RecyclerView al primer elemento
                linearLayoutManager.scrollToPosition(0)
            }
        }



        recyclerViewComments.layoutManager = linearLayoutManager
        recyclerViewComments.adapter = CommentAdapter(comentarios)


        return view
    }
    private fun saveCommentsToJSON(newComment: Comment) {
        // Asegurarse de que el archivo está en almacenamiento interno
        copyJsonToInternalStorage()

        // Leer y parsear el JSON desde almacenamiento interno
        val json = loadJSONFromInternalStorage(requireContext(), "data.json")
        val gson = Gson()
        val categoryList = gson.fromJson(json, CategoryList::class.java)

        // Encuentra la edificación y añade el nuevo comentario
        categoryList.categories.forEach { category ->
            category.edificios.find { it.name == name }?.let { edification ->
                val updatedComments = edification.comments.toMutableList()
                updatedComments.add(0, newComment)
                edification.comments = updatedComments
            }
        }

        // Guarda el JSON actualizado en almacenamiento interno
        val updatedJson = gson.toJson(categoryList)
        requireContext().openFileOutput("data.json", Context.MODE_PRIVATE).use {
            it.write(updatedJson.toByteArray())
        }
    }



    fun getCommentsForEdification(name: String, context: Context): List<Comment> {
        // Obtén las categorías desde el JSON
        val categories = parseCategoriesFromJSON(context)

        // Recorre las categorías y los edificios para encontrar el edificio correspondiente
        for (category in categories) {
            for (edifice in category.edificios) {
                if (edifice.name == name) {
                    return edifice.comments  // Devuelve los comentarios del edificio
                }
            }
        }
        return emptyList()  // Si no se encuentra el edificio, devuelve una lista vacía
    }


    private fun loadJSONFromAsset(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun parseCategoriesFromJSON(context: Context): List<Category> {
        val json = loadJSONFromInternalStorage(context, "data.json")
        val gson = Gson()
        val categoryList = gson.fromJson(json, CategoryList::class.java)
        return categoryList.categories // Retorna solo la lista de categorías
    }

    private fun copyJsonToInternalStorage() {
        val file = File(requireContext().filesDir, "data.json")
        if (!file.exists()) {
            requireContext().assets.open("data.json").use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
    }
    private fun loadJSONFromInternalStorage(context: Context, fileName: String): String {
        return context.openFileInput(fileName).bufferedReader().use { it.readText() }
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
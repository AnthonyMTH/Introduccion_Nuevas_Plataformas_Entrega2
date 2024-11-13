package com.example.myapplication.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.CategoryAdapter
import com.example.myapplication.models.Category
import com.example.myapplication.models.CategoryList
import com.google.gson.Gson
/**
 * A simple [Fragment] subclass.
 * Use the [EdificiosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EdificiosFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edificios, container, false)

        // Obtener el RecyclerView
        val recyclerViewCategories: RecyclerView = view.findViewById(R.id.recyclerViewCategories)

        // Cargar data desde Json
        val categories = parseCategoriesFromJSON(requireContext())


        // Configurar RecyclerView
        recyclerViewCategories.layoutManager = LinearLayoutManager(context)
        recyclerViewCategories.adapter = CategoryAdapter(categories)

        // Configurar escucha de eventos al cambiar buscador
        val searchText = view.findViewById<EditText>(R.id.searchText)

        searchText.addTextChangedListener { text ->

            // Se convierte el texto a minúsculas para hacer la búsqueda no sensible a mayúsculas
            val query = text.toString().trim().lowercase()

            // Se crea una lista temporal de todos los edificios sin importar la categoría
            val allBuildings = categories.flatMap { it.edificios }

            // Se filtra los edificios cuyo nombre contenga el texto ingresado
            val filteredBuildings = if (query.isNotEmpty()) {
                allBuildings.filter { it.name.lowercase().contains(query) }
            } else {
                allBuildings // Si el texto está vacío, mostramos todos los edificios
            }

            // Se agrupa los edificios filtrados nuevamente por categoría para actualizar el adaptador
            val filteredCategories = categories.map { category ->
                val filteredEdificios = filteredBuildings.filter { it in category.edificios }
                Category(category.name, filteredEdificios)
            }.filter { it.edificios.isNotEmpty() } // Eliminamos las categorías sin resultados

            (recyclerViewCategories.adapter as? CategoryAdapter)?.updateCategory(filteredCategories)

        }


        return view
    }

    private fun loadJSONFromAsset(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun parseCategoriesFromJSON(context: Context): List<Category> {
        val json = loadJSONFromAsset(context, "data.json")
        val gson = Gson()
        val categoryList = gson.fromJson(json, CategoryList::class.java)
        return categoryList.categories // Retorna solo la lista de categorías
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EdificiosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EdificiosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
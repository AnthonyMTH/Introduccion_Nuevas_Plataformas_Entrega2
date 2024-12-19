package com.example.myapplication.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MainActivity
import com.example.myapplication.MyApp
import com.example.myapplication.R
import com.example.myapplication.data.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PerfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PerfilFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var db: AppDatabase
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)
        tvUsername = view.findViewById(R.id.userName)
        val userFullInfo: TextView = view.findViewById(R.id.userFullInfo)
        tvEmail = view.findViewById(R.id.userEmail)

        val numPlacesVisited:TextView = view.findViewById(R.id.numPlacesVisited)

        // Recuperar el nombre del usuario desde SharedPreferences
        val sharedPreferences = activity?.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("username", "Invitado") // Valor por defecto si no existe

        rellenarInfoUsuario(username.toString());
        userFullInfo.text = username+"\n21 años\nDe Arequipa, Perú"
        numPlacesVisited.text = "23 lugares visitados"


        // Inflate the layout for this fragment
        return view
    }

    private fun rellenarInfoUsuario(username: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val usuario = MyApp.database.usuarioDao().getUsuarioByUsername(username)

            withContext(Dispatchers.Main) {
                if (usuario != null) {
                    tvUsername.text = "${usuario.username}"
                    tvEmail.text = "${usuario.email}"
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Usuario no encontrado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
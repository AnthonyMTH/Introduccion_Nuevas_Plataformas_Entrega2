package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameEditText: EditText = view.findViewById(R.id.loginUsername)
        val passwordEditText: EditText = view.findViewById(R.id.loginPassword)
        val loginButton: Button = view.findViewById(R.id.loginConfirmBtn)

        // Configurar el botón de inicio de sesión
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Verificar las credenciales
            if (isValidUser(username, password)) {

                // Iniciar MainActivity
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish() // Cerrar la actividad de login
            } else {
                // Si las credenciales no son correctas
                Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para verificar si las credenciales del usuario son correctas
    private fun isValidUser(username: String, password: String): Boolean {
        // Leer el archivo JSON y convertirlo en una lista de usuarios
        val usersList = getUsersFromFile()

        // Buscar si el usuario y contraseña coinciden
        return usersList.any { it.username == username && it.password == password }
    }

    private fun getUsersFromFile(): List<User> {
        val file = File(requireContext().filesDir, "users.json")
        if (!file.exists()) {
            // Si el archivo no existe, significa que no hay usuarios registrados.
            return emptyList()
        }
        // Si el archivo existe, lo leemos y lo deserializamos
        val json = file.readText()
        val gson = Gson()
        val type = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(json, type)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package com.example.myapplication.fragments

import android.content.Context
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
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referencias a los campos del formulario
        val usernameEditText: EditText = view.findViewById(R.id.registerUsername)
        val passwordEditText: EditText = view.findViewById(R.id.regiserPassword)
        val emailEditText: EditText = view.findViewById(R.id.registerEmail)
        val registerButton: Button = view.findViewById(R.id.registerConfirmBtn)

        // Configurar el botón de confirmación de registro
        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val email = emailEditText.text.toString()

            // Leer los usuarios existentes
            val users = getUsersFromFile(requireContext())

            // Verificar si el usuario o correo ya existen
            if (users.any { it.username == username || it.email == email }) {
                Toast.makeText(context, "El usuario o el correo ya están registrados", Toast.LENGTH_SHORT).show()
            } else {
                // Crear y guardar el nuevo usuario
                val newUser = User(username, password, email)
                users.add(newUser)
                saveUsersToFile(requireContext(), users)
                Toast.makeText(context, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()

                // Iniciar MainActivity después del registro
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    // Función para leer usuarios del archivo JSON
    private fun getUsersFromFile(context: Context): MutableList<User> {
        val file = File(context.filesDir, "users.json")
        if (!file.exists()) return mutableListOf()
        val json = file.readText()
        val type = object : TypeToken<MutableList<User>>() {}.type
        return Gson().fromJson(json, type) ?: mutableListOf()
    }

    // Función para guardar usuarios en el archivo JSON
    private fun saveUsersToFile(context: Context, users: List<User>) {
        val file = File(context.filesDir, "users.json")
        val json = Gson().toJson(users)
        file.writeText(json)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
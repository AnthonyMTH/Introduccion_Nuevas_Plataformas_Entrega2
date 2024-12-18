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
import com.example.myapplication.HomeActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.MyApp
import com.example.myapplication.R
import com.example.myapplication.data.local.entities.Usuario
import com.example.myapplication.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

            // Verificar si el usuario ya existe en la base de datos
            CoroutineScope(Dispatchers.IO).launch {
                val existingUser = MyApp.database.usuarioDao().getUsuarioByEmail(email)
                if (existingUser != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "El usuario o correo ya están registrados", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Crear el nuevo usuario y guardarlo en la base de datos
                    val newUser = Usuario(0, username = username, email = email, password = password)
                    MyApp.database.usuarioDao().insert(newUser)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()

                        // Iniciar MainActivity
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
            }
        }
    }
}
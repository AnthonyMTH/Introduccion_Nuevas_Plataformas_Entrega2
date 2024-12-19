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
import com.example.myapplication.MyApp
import com.example.myapplication.R
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

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Por favor ingresa tus credenciales", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar las credenciales con la base de datos
            CoroutineScope(Dispatchers.IO).launch {
                val user = MyApp.database.usuarioDao().getUserByCredentials(username, password)
                withContext(Dispatchers.Main) {
                    if (user != null) {

                        // Guardar el nombre del usuario en SharedPreferences
                        val sharedPreferences = context?.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                        sharedPreferences?.edit()?.apply {
                            putString("username", user.username) // Guardamos el nombre de usuario
                            apply() // Guardamos los cambios
                        }

                        Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    } else {
                        Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}
/*
// Al cerrar sesión:
val sharedPreferences = context?.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
sharedPreferences?.edit()?.clear()?.apply() // Eliminar los datos guardados

// Redirigir al usuario a la pantalla de inicio de sesión
val intent = Intent(context, LoginActivity::class.java)
startActivity(intent)
requireActivity().finish()*/

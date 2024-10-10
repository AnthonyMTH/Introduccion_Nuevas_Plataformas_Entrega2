package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)
        val userName: TextView = view.findViewById(R.id.userName)
        val userFullInfo: TextView = view.findViewById(R.id.userFullInfo)
        val userEmail: TextView = view.findViewById(R.id.userEmail)

        val numPlacesVisited:TextView = view.findViewById(R.id.numPlacesVisited)

        userName.text = "Gabriel"
        userFullInfo.text = "Gabriel Antony\n21 años\nDe Arequipa, Perú"
        userEmail.text = "gabriel@gmail.com"
        numPlacesVisited.text = "23 lugares visitados"


        // Inflate the layout for this fragment
        return view
    }


}
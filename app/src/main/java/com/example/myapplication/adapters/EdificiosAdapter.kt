package com.example.myapplication.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.models.Edificio
import com.example.myapplication.R
import com.example.myapplication.fragments.EdificacionDetailFragment

class EdificiosAdapter(private val edificios: List<Edificio>) : RecyclerView.Adapter<EdificiosAdapter.EdificioViewHolder>() {

    class EdificioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.edificioName)
        val imageView: ImageView = itemView.findViewById(R.id.edificioImage)
        val viewButton: Button = itemView.findViewById(R.id.viewButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EdificioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_edificio, parent, false)
        return EdificioViewHolder(view)
    }

    override fun onBindViewHolder(holder: EdificioViewHolder, position: Int) {
        val edificio = edificios[position]
        holder.nameTextView.text = edificio.name

        Glide.with(holder.itemView.context).load(edificio.imageURL).into(holder.imageView)
        holder.viewButton.setOnClickListener {
            mostrarInformacionEdificacion(holder.itemView.context, edificio)
        }
        //holder.imageView.setImageResource(edificio.imageResource)
        // Agregar lógica de click en el botón, si es necesario
    }
    private fun mostrarInformacionEdificacion(context: Context, edificio: Edificio) {

        val fragment = EdificacionDetailFragment.newInstance(
            name = edificio.name,
            description = "midecripcion",
            imageURL = edificio.imageURL
        )

        val activity = context as? AppCompatActivity
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun getItemCount() = edificios.size
}

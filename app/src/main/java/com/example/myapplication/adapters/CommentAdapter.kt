package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Comment

class CommentAdapter(private val comentarios: List<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {


    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorTextView  = itemView.findViewById<TextView>(R.id.userName)
        val dateTextView = itemView.findViewById<TextView>(R.id.commentTime)
        val commentTextView = itemView.findViewById<TextView>(R.id.commentText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comentario, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comentario = comentarios[position]
        holder.authorTextView.text = comentario.author
        holder.dateTextView.text = comentario.date
        holder.commentTextView.text = comentario.comment



    }

    override fun getItemCount() = comentarios.size
}
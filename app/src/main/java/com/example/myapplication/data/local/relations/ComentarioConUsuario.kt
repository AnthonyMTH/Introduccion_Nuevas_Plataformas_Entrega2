package com.example.myapplication.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myapplication.data.local.entities.Comentario
import com.example.myapplication.data.local.entities.Usuario

data class ComentarioConUsuario(
    @Embedded val comentario: Comentario,
    @Relation(
        parentColumn = "idUsuario",
        entityColumn = "id"
    )
    val usuario: Usuario
)
package com.example.myapplication.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myapplication.data.local.entities.Calificacion
import com.example.myapplication.data.local.entities.Usuario

data class CalificacionConUsuario(
    @Embedded val calificacion: Calificacion,
    @Relation(
        parentColumn = "idUsuario",
        entityColumn = "id"
    )
    val usuario: Usuario
)
package com.example.myapplication.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(entity = Usuario::class, parentColumns = ["id"], childColumns = ["idUsuario"]),
        ForeignKey(entity = Edificacion::class, parentColumns = ["id"], childColumns = ["idEdificacion"])
    ]
)
data class Comentario(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val contenido: String,
    val fecha: String, // Formato Date usando TypeConverter
    val idUsuario: Int,
    val idEdificacion: Int
)

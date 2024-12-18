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
data class Calificacion(
    @PrimaryKey(autoGenerate = true) val idCalificacion: Int,
    val valor: Int,
    val idUsuario: Int,
    val idEdificacion: Int
)

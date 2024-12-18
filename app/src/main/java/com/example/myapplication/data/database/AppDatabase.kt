package com.example.myapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.example.myapplication.data.local.dao.CalificacionDao
import com.example.myapplication.data.local.dao.ComentarioDao
import com.example.myapplication.data.local.dao.EdificacionDao
import com.example.myapplication.data.local.dao.UsuarioDao
import com.example.myapplication.data.local.entities.Edificacion
import com.example.myapplication.data.local.entities.Usuario
import com.example.myapplication.data.local.entities.Comentario
import com.example.myapplication.data.local.entities.Calificacion


@Database(entities = [Edificacion::class, Usuario::class, Comentario::class, Calificacion::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun edificacionDao(): EdificacionDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun comentarioDao(): ComentarioDao
    abstract fun calificacionDao(): CalificacionDao
}
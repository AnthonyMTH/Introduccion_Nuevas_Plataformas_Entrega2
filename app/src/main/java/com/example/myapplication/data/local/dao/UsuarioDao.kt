package com.example.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.local.entities.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insert(usuario: Usuario)

    @Update
    suspend fun update(usuario: Usuario)

    @Delete
    suspend fun delete(usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE id = :id")
    suspend fun getUsuarioById(id: Int): Usuario?

    @Query("SELECT * FROM Usuario WHERE email = :email")
    suspend fun getUsuarioByEmail(email: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE username = :username AND password = :password")
    suspend fun getUserByCredentials(username: String, password: String): Usuario?

    @Query("SELECT * FROM Usuario")
    suspend fun getUsuarios(): List<Usuario>
}

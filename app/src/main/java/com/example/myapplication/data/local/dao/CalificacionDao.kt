package com.example.myapplication.data.local.dao

import androidx.room.Dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myapplication.data.local.entities.Calificacion
import com.example.myapplication.data.local.entities.Usuario
import com.example.myapplication.data.local.relations.CalificacionConUsuario

@Dao
interface CalificacionDao {

    @Transaction
    @Query(
        """
        SELECT * FROM Calificacion 
        INNER JOIN Usuario ON Calificacion.idUsuario = Usuario.id
        WHERE Calificacion.idEdificacion = :idEdificacion
    """
    )
    suspend fun getCalificacionesConUsuario(idEdificacion: Int): List<CalificacionConUsuario>

    @Insert
    suspend fun insertAll(vararg calificaciones: Calificacion)

    //1:M
    @Query("""
    SELECT DISTINCT Usuario.* FROM Usuario
    INNER JOIN Calificacion ON Usuario.id = Calificacion.idUsuario
    WHERE Calificacion.idEdificacion = :idEdificacion
    """)
    suspend fun getUsuariosQueCalificaron(idEdificacion: Int): List<Usuario>



    //N:M
    @Query("""
    SELECT * FROM Calificacion
    INNER JOIN Usuario ON Calificacion.idUsuario = Usuario.id
    """)
    suspend fun getUsuariosConCalificaciones(): List<CalificacionConUsuario>

    @Insert
    suspend fun insertCalificacion(calificacion: Calificacion)

    @Update
    suspend fun updateCalificacion(calificacion: Calificacion)

    @Delete
    suspend fun deleteCalificacion(calificacion: Calificacion)
}

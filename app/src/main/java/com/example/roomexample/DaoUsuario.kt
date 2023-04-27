package com.example.roomexample

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoUsuario {
    @Query("SELECT * FROM usuarios")
    suspend fun obtenerUsuarios(): MutableList<Usuario>

    @Insert
    suspend fun agregarUsuario(usuario: Usuario)

    @Query("UPDATE usuarios set nombre =:nombre WHERE usuario=:usuario")
    suspend fun modificarUsuario(usuario:String, nombre:String)

    @Query("DELETE FROM usuarios WHERE usuario=:usuario")
    suspend fun eliminarUsuario(usuario:String)
}
package com.example.roomexample

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Usuario::class],
    version =1
)
abstract class DBroom:RoomDatabase() {
    abstract fun daoUsuario():DaoUsuario
}
package com.harshjoshi.passwordmanager.data

import android.content.Context
import androidx.room.Room

object Graph {
    lateinit var database: PasswordDatabase

    fun provide(context: Context){
        database = Room.databaseBuilder(
            context,
            PasswordDatabase::class.java,
            "password.db"
        ).build()
    }
}
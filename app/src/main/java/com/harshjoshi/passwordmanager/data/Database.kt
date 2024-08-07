package com.harshjoshi.passwordmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Password::class], version = 1, exportSchema = false)

abstract class PasswordDatabase: RoomDatabase() {
    abstract fun getDao(): PasswordsDao
}
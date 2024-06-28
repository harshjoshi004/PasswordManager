package com.harshjoshi.passwordmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PasswordsDao{
    @Upsert
    abstract suspend fun upsertPassword(password: Password)
    @Delete
    abstract suspend fun deletePassword(password: Password)
    @Query("Select * from `password_table`")
    abstract fun getPasswords(): Flow<List<Password>>
    @Query("Select * from `password_table` where id = :id")
    abstract fun getPassword(id: Long): Flow<Password>
}
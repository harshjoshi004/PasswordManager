package com.harshjoshi.passwordmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_table")
data class Password (
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    @ColumnInfo("password")
    val password:String,
    @ColumnInfo("username")
    val username:String,
    @ColumnInfo("account_type")
    val accountType:String
)
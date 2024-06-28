package com.harshjoshi.passwordmanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.harshjoshi.passwordmanager.data.Graph
import com.harshjoshi.passwordmanager.data.Password
import com.harshjoshi.passwordmanager.data.PasswordsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PasswordManagerViewModel(): ViewModel() {
    private val passwordsDao: PasswordsDao = Graph.database.getDao()
    lateinit var allPasswords: Flow<List<Password>>
    init {
        getAllPasswords()
    }
    fun getAllPasswords(){
        allPasswords= passwordsDao.getPasswords()
    }
    fun upsertPass(password: Password){
        viewModelScope.launch {
            passwordsDao.upsertPassword(password)
        }
    }
    fun deletePass(password: Password){
        viewModelScope.launch {
            passwordsDao.deletePassword(password)
        }
    }
    fun getPassById(id: Long): Flow<Password>{
        return passwordsDao.getPassword(id)
    }
}
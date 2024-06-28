package com.harshjoshi.passwordmanager

import android.app.Application
import com.harshjoshi.passwordmanager.data.Graph

class PasswordManagerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}
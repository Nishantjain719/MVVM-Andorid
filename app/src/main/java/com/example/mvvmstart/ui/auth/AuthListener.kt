package com.example.mvvmstart.ui.auth

import androidx.lifecycle.LiveData
import com.example.mvvmstart.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}
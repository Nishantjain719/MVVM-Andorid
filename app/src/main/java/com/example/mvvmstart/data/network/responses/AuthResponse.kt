package com.example.mvvmstart.data.network.responses

import com.example.mvvmstart.data.db.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?

)

package com.example.mvvmstart.data.repositories

import com.example.mvvmstart.data.db.AppDatabase
import com.example.mvvmstart.data.db.entities.User
import com.example.mvvmstart.data.network.MyApi
import com.example.mvvmstart.data.network.SafeApiRequest
import com.example.mvvmstart.data.network.responses.AuthResponse

import retrofit2.Response

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest(){
    // using coroutines we simplify our network calls
    suspend fun userLogin(email: String, password: String): AuthResponse{
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}
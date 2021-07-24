package com.example.mvvmstart.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.mvvmstart.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    context: Context
): Interceptor {
    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {

        if(!isInternet()){

            throw NoInternetException("Make sure internet is on")
        }
        return chain.proceed(chain.request())
    }

    private fun isInternet() : Boolean{

        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}
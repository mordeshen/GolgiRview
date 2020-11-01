package com.e.golgirview.session

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager
@Inject
constructor(
    val application: Application
) {
    private val TAG = "SessionManager"


    fun isConnectedToInternet(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            return cm.activeNetworkInfo?.isConnected!!
        } catch (e: Exception) {
            Log.e(TAG, "isConnectedToTheInternet: ${e.message}")
        }
        return false
    }
}


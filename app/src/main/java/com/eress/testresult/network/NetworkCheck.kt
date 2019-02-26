package com.eress.testresult.network

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

class NetworkCheck(private val activity: Activity) {

    fun info(): Boolean {
        val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (cm is ConnectivityManager) {
            val networkInfo = cm.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
        else false
    }
}
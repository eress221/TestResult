package com.eress.testresult.common

import android.app.Application

class App : Application() {

    companion object {
        @get: Synchronized
        lateinit var init: App
        private set
    }

    override fun onCreate() {
        super.onCreate()
        init = this
    }
}

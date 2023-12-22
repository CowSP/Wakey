package com.example.wakey

import android.app.Application
import com.example.wakey.data.AppContainer
import com.example.wakey.data.AppDataContainer

class WakeyApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
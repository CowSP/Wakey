package com.example.wakey.data

import android.content.Context

interface AppContainer {
    val wakeyRepository: WakeyRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val wakeyRepository: WakeyRepository by lazy {
        OfflineWakeyRepository(WakeyDatabase.getDatabase(context).wakeyDao())
    }
}
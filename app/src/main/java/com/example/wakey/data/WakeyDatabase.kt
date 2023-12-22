package com.example.wakey.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Alarm::class, Pattern::class, Puzzle::class, Tick::class, Tone::class],
    version = 1,
    exportSchema = false
)
abstract class WakeyDatabase : RoomDatabase() {
    abstract fun wakeyDao(): WakeyDao

    companion object {
        @Volatile
        private var Instance: WakeyDatabase? = null

        fun getDatabase(context: Context): WakeyDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, WakeyDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
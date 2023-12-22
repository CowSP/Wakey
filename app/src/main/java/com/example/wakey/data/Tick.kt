package com.example.wakey.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ticks")
data class Tick(
    @PrimaryKey(autoGenerate = true)
    val tickId: Int,
    val patternId: Int,
    val delayMinutes: Int,
    val toneId: Int,
    val volume: Int
)

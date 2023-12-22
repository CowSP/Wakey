package com.example.wakey.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tones")
data class Tone(
    @PrimaryKey(autoGenerate = true)
    val toneId: Int,
    val name: String,
    val filePath: String
)

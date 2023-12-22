package com.example.wakey.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patterns")
data class Pattern(
    @PrimaryKey(autoGenerate = true)
    val patternId: Int,
    val name: String
)
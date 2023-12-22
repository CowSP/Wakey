package com.example.wakey.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "puzzles")
data class Puzzle(
    @PrimaryKey(autoGenerate = true)
    val puzzleId: Int,
    val name: String
)

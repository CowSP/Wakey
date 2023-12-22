package com.example.wakey.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val alarmId: Int,
    val name: String,
    val triggerHour: Int,
    val triggerMinute: Int,
    val triggerOnSunday: Boolean,
    val triggerOnMonday: Boolean,
    val triggerOnTuesday: Boolean,
    val triggerOnWednesday: Boolean,
    val triggerOnThursday: Boolean,
    val triggerOnFriday: Boolean,
    val triggerOnSaturday: Boolean,
    val patternId: Int,
    val snooze: Boolean,
    val vibrate: Boolean,
    val puzzleId: Int
)

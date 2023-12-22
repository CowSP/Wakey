package com.example.wakey.data

import kotlinx.coroutines.flow.Flow

interface WakeyRepository {
    // Alarm Functions
    suspend fun addAlarm(alarm: Alarm)
    suspend fun editAlarm(alarm: Alarm)
    suspend fun deleteAlarm(alarm: Alarm)
    fun getAllAlarms(): Flow<List<Alarm>>
    fun getAlarmById(alarmId: Int): Flow<Alarm>

    // Pattern Functions
    suspend fun addPattern(pattern: Pattern)
    suspend fun editPattern(pattern: Pattern)
    suspend fun deletePattern(pattern: Pattern)
    fun getAllPatterns(): Flow<List<Pattern>>
    fun getPatternById(patternId: Int): Flow<Pattern>

    // Puzzle Functions
    fun getAllPuzzles(): Flow<List<Puzzle>>
    fun getPuzzleById(puzzleId: Int): Flow<Puzzle>

    // Tick Functions
    suspend fun addTick(tick: Tick)
    suspend fun editTick(tick: Tick)
    suspend fun deleteTick(tick: Tick)
    fun getTicksByPatternId(patternId: Int): Flow<List<Tick>>
    fun getTickByTickId(tickId: Int): Flow<Tick>

    // Tone Functions
    suspend fun addTone(tone: Tone)
    suspend fun deleteTone(tone: Tone)
    fun getAllTones(): Flow<List<Tone>>
    fun getToneById(toneId: Int): Flow<Tone>
}
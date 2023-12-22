package com.example.wakey.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WakeyDao {
    // Alarm Functions
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAlarm(alarm: Alarm)

    @Update
    suspend fun updateAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

    @Query("SELECT * FROM alarms ORDER BY triggerHour, triggerMinute ASC")
    fun selectAllAlarms(): Flow<List<Alarm>>

    @Query("SELECT * FROM alarms WHERE alarmId = :alarmId")
    fun selectAlarmById(alarmId: Int): Flow<Alarm>

    // Pattern Functions
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPattern(pattern: Pattern)

    @Update
    suspend fun updatePattern(pattern: Pattern)

    @Delete
    suspend fun deletePattern(pattern: Pattern)

    @Query("SELECT * FROM patterns ORDER BY name ASC")
    fun selectAllPatterns(): Flow<List<Pattern>>

    @Query("SELECT * FROM patterns WHERE patternId = :patternId")
    fun selectPatternById(patternId: Int): Flow<Pattern>

    // Puzzle Functions
    @Query("SELECT * FROM puzzles ORDER BY name ASC")
    fun selectAllPuzzles(): Flow<List<Puzzle>>

    @Query("SELECT * FROM puzzles WHERE puzzleId = :puzzleId")
    fun selectPuzzleById(puzzleId: Int): Flow<Puzzle>

    // Tick Functions
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTick(tick: Tick)

    @Update
    suspend fun updateTick(tick: Tick)

    @Delete
    suspend fun deleteTick(tick: Tick)

    @Query("SELECT * FROM ticks WHERE patternId = :patternId ORDER BY delayMinutes ASC")
    fun selectTicksByPatternId(patternId: Int): Flow<List<Tick>>

    @Query("SELECT * FROM ticks WHERE tickId = :tickId")
    fun selectTickByTickId(tickId: Int): Flow<Tick>

    // Tone Functions
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTone(tone: Tone)

    @Delete
    suspend fun deleteTone(tone: Tone)

    @Query("SELECT * FROM tones ORDER BY name ASC")
    fun selectAllTones(): Flow<List<Tone>>

    @Query("SELECT * FROM tones WHERE toneId = :toneId")
    fun selectToneById(toneId: Int): Flow<Tone>
}
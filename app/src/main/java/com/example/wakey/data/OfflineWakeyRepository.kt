package com.example.wakey.data

import kotlinx.coroutines.flow.Flow

class OfflineWakeyRepository(
    private val wakeyDao: WakeyDao
) : WakeyRepository {
    // Alarm Functions
    override suspend fun addAlarm(alarm: Alarm) = wakeyDao.insertAlarm(alarm)
    override suspend fun editAlarm(alarm: Alarm) = wakeyDao.updateAlarm(alarm)
    override suspend fun deleteAlarm(alarm: Alarm) = wakeyDao.deleteAlarm(alarm)
    override fun getAllAlarms(): Flow<List<Alarm>> = wakeyDao.selectAllAlarms()
    override fun getAlarmById(alarmId: Int): Flow<Alarm> = wakeyDao.selectAlarmById(alarmId)

    // Pattern Functions
    override suspend fun addPattern(pattern: Pattern) = wakeyDao.insertPattern(pattern)
    override suspend fun editPattern(pattern: Pattern) = wakeyDao.updatePattern(pattern)
    override suspend fun deletePattern(pattern: Pattern) = wakeyDao.deletePattern(pattern)
    override fun getAllPatterns(): Flow<List<Pattern>> = wakeyDao.selectAllPatterns()
    override fun getPatternById(patternId: Int): Flow<Pattern> = wakeyDao.selectPatternById(patternId)

    // Puzzle Functions
    override fun getAllPuzzles(): Flow<List<Puzzle>> = wakeyDao.selectAllPuzzles()
    override fun getPuzzleById(puzzleId: Int): Flow<Puzzle> = wakeyDao.selectPuzzleById(puzzleId)

    // Tick Functions
    override suspend fun addTick(tick: Tick) = wakeyDao.insertTick(tick)
    override suspend fun editTick(tick: Tick) = wakeyDao.updateTick(tick)
    override suspend fun deleteTick(tick: Tick) = wakeyDao.deleteTick(tick)
    override fun getTicksByPatternId(patternId: Int): Flow<List<Tick>> =
        wakeyDao.selectTicksByPatternId(patternId)
    override fun getTickByTickId(tickId: Int): Flow<Tick> = wakeyDao.selectTickByTickId(tickId)

    // Tone Functions
    override suspend fun addTone(tone: Tone) = wakeyDao.insertTone(tone)
    override suspend fun deleteTone(tone: Tone) = wakeyDao.deleteTone(tone)
    override fun getAllTones(): Flow<List<Tone>> = wakeyDao.selectAllTones()
    override fun getToneById(toneId: Int): Flow<Tone> = wakeyDao.selectToneById(toneId)
}
package com.example.android_project.dao

import androidx.room.*
import com.example.android_project.ExerciseStat
import com.example.android_project.models.History

@Dao
interface HistoryDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertHistory(history: com.example.android_project.models.History): Long
//
//    @Update
//    suspend fun updateHistory(history: com.example.android_project.models.History)
//
//    @Delete
//    suspend fun deleteHistory(history: com.example.android_project.models.History)
//
//    @Query("SELECT * FROM history WHERE id = :id")
//    suspend fun getHistoryById(id: Int): com.example.android_project.models.History?

    @Query("SELECT * FROM history WHERE exerciseId = :exerciseId")
    suspend fun getHistoriesByExerciseId(exerciseId: Int): List<History>

    @Query("SELECT date, COUNT(exerciseId) AS count FROM history GROUP BY date ORDER BY date ASC")
    suspend fun getExercisesByDate(): List<ExerciseStat>

//    @Transaction
//    @Query("SELECT * FROM user WHERE id = :userId")
//    suspend fun getUserWithUserActivities(userId: Int): UserWithUserActivities
}

package com.example.android_project.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android_project.models.Exercise

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExercises(exercises: List<Exercise>)

    @Query("SELECT * FROM exercise WHERE lessonId = :lessonId")
    suspend fun getExercisesByLessonId(lessonId: Int): List<Exercise>
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertExercise(exercise: com.example.android_project.models.Exercise): Long

//    @Update
//    suspend fun updateExercise(exercise: com.example.android_project.models.Exercise)
//
//    @Delete
//    suspend fun deleteExercise(exercise: com.example.android_project.models.Exercise)
//
//    @Query("SELECT * FROM exercise WHERE id = :id")
//    suspend fun getExerciseById(id: Int): com.example.android_project.models.Exercise?
//
//    @Query("SELECT * FROM exercise WHERE lessonId = :lessonId")
//    suspend fun getExercisesByLessonId(lessonId: Int): List<com.example.android_project.models.Exercise>
//
//    @Transaction
//    @Query("SELECT * FROM exercise")
//    suspend fun getAllExercisesWithHistories(): List<com.example.android_project.relationships.ExerciseWithHistories>

    @Query("SELECT * FROM exercise")
    suspend fun getAllExercises(): List<Exercise>
}

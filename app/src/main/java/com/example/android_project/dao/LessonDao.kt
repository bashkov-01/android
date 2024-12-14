package com.example.android_project.dao

import androidx.room.*
import com.example.android_project.models.History
import com.example.android_project.models.Lesson


@Dao
interface LessonDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertLesson(lesson: com.example.android_project.models.Lesson): Long
//
//    @Update
//    suspend fun updateLesson(lesson: com.example.android_project.models.Lesson)
//
//    @Delete
//    suspend fun deleteLesson(lesson: com.example.android_project.models.Lesson)
//
//    @Query("SELECT * FROM lesson WHERE id = :id")
//    suspend fun getLessonById(id: Int): com.example.android_project.models.Lesson?
//
//    @Transaction
//    @Query("SELECT * FROM lesson")
//    suspend fun getAllLessonsWithExercises(): List<com.example.android_project.relationships.LessonWithExercises>

    @Query("SELECT * FROM lesson")
    suspend fun getAllLessons(): List<Lesson>

    @Query("SELECT * FROM lesson WHERE id IN (:lessonId)")
    suspend fun getLessonsByLessonId(lessonId: List<Int>): List<Lesson>
}

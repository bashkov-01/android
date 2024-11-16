package com.example.android_project

import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.android_project.dao.ExerciseDao
import com.example.android_project.dao.HistoryDao
import com.example.android_project.dao.LessonDao
import com.example.android_project.dao.UserDao
import com.example.android_project.models.Exercise
import com.example.android_project.models.History
import com.example.android_project.models.Lesson
import com.example.android_project.models.Userr


@Database(entities = [Userr::class, Lesson::class, History::class, Exercise::class], version = 1)
abstract class MainDb : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun lessonDao(): LessonDao
    abstract fun historyDao(): HistoryDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: MainDb? = null

        fun getDb(context: Context): MainDb {
            Log.d("MainDb", "Инициализация базы данных...")

            // Проверка на наличие уже созданного экземпляра базы данных
            return INSTANCE ?: synchronized(this) {
                try {
                    Log.d("MainDb", "Создание нового экземпляра базы данных...")
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MainDb::class.java,
                        "main.db"
                    )
                        .fallbackToDestructiveMigration()  // Используйте для сброса БД при изменении схемы
                        .build()

                    INSTANCE = instance
                    Log.d("MainDb", "Экземпляр базы данных создан.")
                    instance
                } catch (e: Exception) {
                    Log.e("MainDb", "Ошибка при создании базы данных", e)
                    throw e
                }
            }
        }
    }
}
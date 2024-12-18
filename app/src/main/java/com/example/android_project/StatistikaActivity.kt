package com.example.android_project

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StatistikaActivity : AppCompatActivity() {
    private lateinit var statisticsText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistika)

        // Инициализация TextView для отображения статистики
        statisticsText = findViewById(R.id.statisticsTextView)

        // Запрос статистики за текущую неделю
        lifecycleScope.launch {
            val stats = getWeeklyStats()
            statisticsText.text = stats
        }
    }

    // Метод для получения статистики за текущую неделю
    private suspend fun getWeeklyStats(): String {
        return withContext(Dispatchers.IO) {
            val historyDao = MainDb.getDb(this@StatistikaActivity).historyDao()

            // Получаем текущий день недели и начало недели (понедельник)
            val today = LocalDate.now()
            val startOfWeek = today.minusDays(today.dayOfWeek.value.toLong() - 1)

            // Формируем список всех дней недели
            val weekDays = (0..6).map { startOfWeek.plusDays(it.toLong()) }

            // Получаем статистику из базы
            val exercises = historyDao.getExercisesByDate()

            // Преобразуем данные в map для быстрого доступа
            val exerciseMap = exercises.associateBy { it.date }

            // Формируем строку статистики для каждого дня
            val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            weekDays.joinToString("\n") { day ->
                val dayString = day.format(dateFormatter)
                val count = exerciseMap[dayString]?.count ?: 0
                "Date: $dayString, Exercises: $count"
            }
        }
    }
}
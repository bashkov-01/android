package com.example.android_project

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue

class StatistikaActivity : AppCompatActivity() {
    private lateinit var statisticsText: TextView
    private lateinit var pnView: View
    private lateinit var vtView: View
    private lateinit var srView: View
    private lateinit var chView: View
    private lateinit var ptView: View
    private lateinit var sbView: View
    private lateinit var vsView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistika)

        pnView = findViewById(R.id.pnView)
        vtView = findViewById(R.id.vtView)
        srView = findViewById(R.id.srView)
        chView = findViewById(R.id.chView)
        ptView = findViewById(R.id.ptView)
        sbView = findViewById(R.id.sbView)
        vsView = findViewById(R.id.vsView)

        lifecycleScope.launch {
            val statsMap = getWeeklyStats()
            val statsText = statsMap.entries.joinToString("\n") { (date, count) ->
                "Date: $date, Exercises: $count"
            }
//            statisticsText.text = statsText

            updateViewWidths(statsMap)
        }
    }

    private fun updateViewWidths(exerciseCounts: Map<String, Int>) {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val today = LocalDate.now()
        val startOfWeek = today.minusDays(today.dayOfWeek.value.toLong() - 1)
        val weekDays = (0..6).map { startOfWeek.plusDays(it.toLong()) }

        if(today.dayOfWeek.value == 1)
            pnView.backgroundTintList = ContextCompat.getColorStateList(this, R.color.textColor)

        if(today.dayOfWeek.value == 2)
            vtView.backgroundTintList = ContextCompat.getColorStateList(this, R.color.textColor)

        if(today.dayOfWeek.value == 3)
            srView.backgroundTintList = ContextCompat.getColorStateList(this, R.color.textColor)

        if(today.dayOfWeek.value == 4)
            chView.backgroundTintList = ContextCompat.getColorStateList(this, R.color.textColor)

        if(today.dayOfWeek.value == 5)
            ptView.backgroundTintList = ContextCompat.getColorStateList(this, R.color.textColor)

        if(today.dayOfWeek.value == 6)
            sbView.backgroundTintList = ContextCompat.getColorStateList(this, R.color.textColor)

        if(today.dayOfWeek.value == 7)
            vsView.backgroundTintList = ContextCompat.getColorStateList(this, R.color.textColor)





        val views = listOf(pnView, vtView, srView, chView, ptView, sbView, vsView)

        weekDays.forEachIndexed { index, day ->
            val dayString = day.format(dateFormatter)
            val count = exerciseCounts[dayString] ?: 0
            val view = views[index]
            view.layoutParams = view.layoutParams.apply {
                width = (50 * (count+1))
                // Ensure initial width is maintained and only grows to the right
            }

            if(exerciseCounts[dayString] ?: 0 != 0)
                view.visibility = View.VISIBLE
            else
                view.visibility = View.INVISIBLE
            view.requestLayout()
        }
    }

    // Метод для получения статистики за текущую неделю
    private suspend fun getWeeklyStats(): Map<String, Int> {
        return withContext(Dispatchers.IO) {
            val historyDao = MainDb.getDb(this@StatistikaActivity).historyDao()
            val today = LocalDate.now()
            val startOfWeek = today.minusDays(today.dayOfWeek.value.toLong() - 1)
            val weekDays = (0..6).map { startOfWeek.plusDays(it.toLong()) }
            val exercises = historyDao.getExercisesByDate()
            val exerciseMap = exercises.associateBy { it.date }

            // Возвращаем карту с количеством упражнений
            val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            weekDays.associate { day ->
                val dayString = day.format(dateFormatter)
                dayString to (exerciseMap[dayString]?.count ?: 0)
            }
        }
    }
}
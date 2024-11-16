package com.example.android_project

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LessonActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        val cardContainer = findViewById<LinearLayout>(R.id.cardContainer)
        val db = MainDb.getDb(applicationContext)

        // Получаем данные из БД
        lifecycleScope.launch {
            val lessonList = db.lessonDao().getAllLessons()

            // Создаем карточки для каждого занятия
            for (index in lessonList.indices) {
                val lesson = lessonList[index]
                val imageName = "picture${index + 1}"
                val imageResId = resources.getIdentifier(imageName, "drawable", packageName)
                // Создаем карточку
                val cardView = CardView(this@LessonActivity).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 0, 0, 16)
                    }
                    radius = 16f
                    cardElevation = 8f
                }

                // Создаем макет содержимого карточки
                val cardContent = LinearLayout(this@LessonActivity).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(16, 16, 16, 16)
                }

                // Добавляем изображение
                val imageView = ImageView(this@LessonActivity).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        200
                    )
                    setImageResource(imageResId)
                }

                // Добавляем текстовое поле для названия
                val titleTextView = TextView(this@LessonActivity).apply {
                    text = lesson.titleOfLesson
                    textSize = 18f
                    setPadding(0, 16, 0, 8)
                }

                // Добавляем текстовое поле для описания
                val descriptionTextView = TextView(this@LessonActivity).apply {
                    text = lesson.descriptionOfLesson
                    textSize = 14f
                    setPadding(0, 0, 0, 16)
                }

                // Добавляем кнопку
                val button = Button(this@LessonActivity).apply {
                    text = "Перейти"
                    //Тут будет переход на занятие
                }

                // Добавляем все элементы в содержимое карточки
                cardContent.addView(imageView)
                cardContent.addView(titleTextView)
                cardContent.addView(descriptionTextView)
                cardContent.addView(button)

                // Добавляем содержимое в карточку
                cardView.addView(cardContent)

                // Добавляем карточку в контейнер
                cardContainer.addView(cardView)
            }
        }
    }
}
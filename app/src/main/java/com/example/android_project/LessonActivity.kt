package com.example.android_project

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LessonActivity : AppCompatActivity() {
    private var lessonId = 0

    private val sharedPreferences by lazy {
        getSharedPreferences("lessons", MODE_PRIVATE)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        val cardContainer = findViewById<LinearLayout>(R.id.cardContainer)
        val db = MainDb.getDb(applicationContext)

        lessonId = intent.getIntExtra("lesson_id_exercise", -1)
        if (lessonId != -1) {
            markLessonAsCompleted(lessonId)
        }

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
                    radius = 24f
                    cardElevation = 8f
                    setCardBackgroundColor(Color.parseColor("#EFF7FF"))
                }

                // Создаем макет содержимого карточки
                val cardContent = LinearLayout(this@LessonActivity).apply {
                    orientation = LinearLayout.HORIZONTAL
                    setPadding(16, 16, 16, 16)
                }

                // Добавляем изображение
                val imageView = ImageView(this@LessonActivity).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        3f // Задаем вес 1 для изображения
                    )
                    adjustViewBounds = true
                    setImageResource(imageResId)
                }

                // Создаем вертикальный макет для текстовых полей и кнопки
                val textLayout = LinearLayout(this@LessonActivity).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        2f // Задаем вес 3 для текстов и кнопки
                    )
                }
                val timeCardContent = LinearLayout(this@LessonActivity).apply {
                    orientation = LinearLayout.HORIZONTAL
                    setPadding(4, 4, 4, 4)
                }

                val titleTimeView = TextView(this@LessonActivity).apply {
                    //ВСТАВИТЬ МИНУТЫ
                    text = "10 МИНУТ"
                    textSize = 14f
                    setTypeface(null, Typeface.NORMAL)
                    setPadding(4, 4, 4, 4)
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    background = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        cornerRadius = 4f  // Закругленные углы
                        setColor(Color.WHITE)  // Цвет фона
                    }
                }
                val titleEmptyView = TextView(this@LessonActivity).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                }

                timeCardContent.addView(titleTimeView)
                timeCardContent.addView(titleEmptyView)
                textLayout.addView(timeCardContent)

                // Добавляем текстовое поле для названия
                val titleTextView = TextView(this@LessonActivity).apply {
                    text = lesson.titleOfLesson
                    textSize = 20f
                    setTypeface(null, Typeface.BOLD)
                    setPadding(0, 16, 0, 8)
                }

                // Добавляем текстовое поле для описания
                val descriptionTextView = TextView(this@LessonActivity).apply {
                    text = lesson.descriptionOfLesson
                    textSize = 14f
                    setPadding(0, 0, 0, 16)
                }

                // Добавляем кнопку
                val button = AppCompatButton(this@LessonActivity).apply {
                    val isCompleted = isLessonCompleted(lesson.id)
                    if (isCompleted) {
                        text = "Выполнено"
                        isEnabled = false  // Делаем кнопку недоступной
                        setTextColor(Color.GRAY)  // Цвет текста
                        background = GradientDrawable().apply {
                            shape = GradientDrawable.RECTANGLE
                            cornerRadius = 16f
                            setColor(Color.LTGRAY)  // Серый фон для неактивной кнопки
                        }
                    }
                    else
                    {
                        text = "Перейти"
                        setBackgroundColor(Color.parseColor("#2E4052"))  // Устанавливаем цвет фона
                        setTextColor(Color.WHITE)  // Устанавливаем цвет текста
                        setPadding(0, 16, 0, 16)  // Устанавливаем padding для кнопки

                        // Устанавливаем параметры разметки
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            gravity = Gravity.CENTER_HORIZONTAL  // Размещаем кнопку по центру
                            setMargins(0, 16, 0, 0)  // Добавляем внешние отступы
                        }

                        // Устанавливаем закругленные углы
                        background = GradientDrawable().apply {
                            shape = GradientDrawable.RECTANGLE
                            cornerRadius = 16f  // Закругленные углы
                            setColor(Color.parseColor("#2E4052"))  // Цвет фона
                        }

                        setOnClickListener {
                            // Переход на активность с упражнениями, передаем id занятия
                            val intent = Intent(this@LessonActivity, ExerciseActivity::class.java)
                            intent.putExtra("lesson_id", lesson.id)  // Передаем id занятия
                            startActivity(intent)
                        }
                    }
                }

                // Добавляем текстовые поля и кнопку в вертикальный макет
                textLayout.addView(titleTextView)
                textLayout.addView(descriptionTextView)
                textLayout.addView(button)

                // Добавляем изображение и текстовый макет в содержимое карточки

                cardContent.addView(textLayout)
                cardContent.addView(imageView)

                // Добавляем содержимое в карточку
                cardView.addView(cardContent)

                // Добавляем карточку в контейнер
                cardContainer.addView(cardView)
            }
        }
    }

    // Метод для пометки занятия как выполненного
    private fun markLessonAsCompleted(lessonId: Int) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("lesson_$lessonId", true)
        editor.apply()
    }

    // Метод для проверки, выполнено ли занятие
    private fun isLessonCompleted(lessonId: Int?): Boolean {
        return sharedPreferences.getBoolean("lesson_$lessonId", false)
    }
}
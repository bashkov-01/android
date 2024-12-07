package com.example.android_project

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.launch

class ExercisesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_exercises)

        val cardContainer = findViewById<LinearLayout>(R.id.cardContainerAll)  // Контейнер для карточек
        val db = MainDb.getDb(applicationContext)

        lifecycleScope.launch {
            val exercisesList = db.exerciseDao().getAllExercises()  // Получаем список упражнений

            for (index in exercisesList.indices) {
                val exercise = exercisesList[index]

                // Получаем имя картинки для текущего упражнения (например, "exercise1")
                val imageName = "exercise${index + 1}"
                val imageResId = resources.getIdentifier(imageName, "drawable", packageName)

                // Создаем карточку для упражнения
                val cardView = CardView(this@ExercisesActivity).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        800
                    ).apply {
                        setMargins(20, 20, 20, 20)
                    }
                    radius = 16f
                    cardElevation = 4f
                    setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                }

                // Создаем макет содержимого карточки
                val cardContent = LinearLayout(this@ExercisesActivity).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(32, 32, 32, 32)
                }
                // Добавляем изображение
                val imageView = ImageView(this@ExercisesActivity).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        7f  // Вес 5 для изображения
                    )
                    adjustViewBounds = true
                    setImageResource(imageResId)
                }

                Glide.with(this@ExercisesActivity)
                    .load(imageResId)
                    .transform(RoundedCorners(16))  // Закругленные края с радиусом 16 пикселей
                    .into(imageView)
                // Создаем горизонтальный макет для текста
                val textLayout = LinearLayout(this@ExercisesActivity).apply {
                    orientation = LinearLayout.HORIZONTAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        2f  // Вес 2 для текста
                    )
                }
                // Добавляем название
                val titleTextView = TextView(this@ExercisesActivity).apply {
                    text = exercise.titleOfExercise // Здесь вы можете использовать поле из вашей базы данных для названия
                    textSize = 20f
                    setTypeface(null, Typeface.BOLD)
                    setTextColor(getColor(R.color.textColor))
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        3f  // Вес 1 для выравнивания по левому краю
                    )
                }
                // Добавляем тайминг
                val timeTextView = TextView(this@ExercisesActivity).apply {
                    text = "${exercise.timerOfExercise} секунд" // Здесь можно взять продолжительность из базы данных
                    textSize = 10f
                    setTextColor(Color.parseColor("#888888"))
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1f  // Вес 1 для выравнивания по правому краю
                    )
                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_END
                }
                // Добавляем описание
                val descriptionTextView = TextView(this@ExercisesActivity).apply {
                    text = exercise.descriptionOfExercise // Здесь описание из базы данных
                    textSize = 14f
                    setTextColor(getColor(R.color.textColor))
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        2f  // Вес 3 для описания
                    )
                    setPadding(0, 0, 0, 16)
                }

                // Добавляем все элементы в горизонтальный макет
                textLayout.addView(titleTextView)
                textLayout.addView(timeTextView)

                cardView.setOnClickListener {
                    // Переход к активности с деталями упражнения
                    val intent = Intent(this@ExercisesActivity, ExerciseActivityFromExercises::class.java)
                    intent.putExtra("lesson_id_from_exercises", exercise.id)  // Передаем id упражнения
                    startActivity(intent)
                }

                // Добавляем текстовый макет и изображение в содержимое карточки
                cardContent.addView(imageView)
                cardContent.addView(textLayout)
                cardContent.addView(descriptionTextView)

                // Добавляем содержимое в карточку
                cardView.addView(cardContent)

                // Добавляем карточку в контейнер
                cardContainer.addView(cardView)
            }
        }
    }
}

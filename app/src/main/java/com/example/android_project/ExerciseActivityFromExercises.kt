package com.example.android_project

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.android_project.models.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExerciseActivityFromExercises : AppCompatActivity() {
    private var timer: CountDownTimer? = null
    private lateinit var textViewTitle: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var button: Button
    private lateinit var gifImageView: ImageView
    private lateinit var textViewExercise: TextView
    private lateinit var buttonBack: ImageButton
    private var lessonId = 0

    private var currentExerciseIndex = 0  // Текущий индекс упражнения
    private var exercises = listOf<Exercise>()  // Список упражнений
    private var isTimerRunning = false // Флаг состояния таймера
    private var mediaPlayer: MediaPlayer? = null
    private var isPaused = false // Флаг паузы
    private var timeLeftInMillis: Long = 0 // Оставшееся время на таймере
    private var countExercicesString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_from_exercises)

        gifImageView = findViewById(R.id.gifImage)
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription = findViewById(R.id.textViewDescription)
        textViewExercise = findViewById(R.id.textView4)
        buttonBack = findViewById(R.id.imageButton2)
        button = findViewById(R.id.button2)

        // Получаем id занятия, переданный через Intent
        lessonId = intent.getIntExtra("lesson_id_from_exercises", -1)

        buttonBack.setOnClickListener {
            stopAudio()
            val intent = Intent(this@ExerciseActivityFromExercises, ExercisesActivity::class.java)
            startActivity(intent)
        }

        if (lessonId != -1) {
            val exerciseDao = MainDb.getDb(this).exerciseDao()
//            val countExercices = exerciseDao.getCountOfExercises()


            // Загружаем упражнения по lessonId
            GlobalScope.launch(Dispatchers.Main) {
                val countExercices = exerciseDao.getCountOfExercises()
                countExercicesString = countExercices.toString()
                exercises = exerciseDao.getExercisesById(lessonId)

                if (exercises.isNotEmpty()) {
                    showExercise(0) // Показать первое упражнение

                    button.setOnClickListener {
                        handleButtonClick()
                    }
                }
            }
        }
    }

    private fun handleButtonClick() {
        when {
            isTimerRunning && !isPaused -> {
                pauseExerciseTimer()
            }
            isPaused -> {
                resumeExerciseTimer()
            }
            timer == null -> {
                val time = exercises[currentExerciseIndex].timerOfExercise
                startExerciseTimer(time)
            }
        }
    }

    private fun pauseExerciseTimer() {
        timer?.cancel()
        isPaused = true
        isTimerRunning = false
        button.text = "Продолжить"
    }

    private fun resumeExerciseTimer() {
        startExerciseTimer(timeLeftInMillis / 1000)
        isPaused = false
        isTimerRunning = true
        stopAudio()
    }

    private fun startExerciseTimer(timeInSeconds: Long) {
        timer?.cancel()
        isTimerRunning = true
        isPaused = false
        button.text = "Пауза"

        playAudio()

        timer = object : CountDownTimer(timeInSeconds * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                val secondsLeft = millisUntilFinished / 1000
                button.text = formatTime(secondsLeft.toInt())
            }

            override fun onFinish() {
                button.text = "Далее"
                isTimerRunning = false
                timer = null
                stopAudio()
                showCompletionDialog()
            }
        }.start()
    }

    private fun showCompletionDialog() {
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
        dialogBuilder.setTitle("Упражнение завершено")
            .setMessage("Поздравляем! Вы успешно завершили упражнение.")
            .setPositiveButton("ОК") { dialog, _ ->
                dialog.dismiss()
                moveToNextExercise()
            }
            .setCancelable(false)

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun moveToNextExercise() {
        currentExerciseIndex++
        if (currentExerciseIndex < exercises.size) {
            showExercise(currentExerciseIndex)
        } else {
            finishActivity()
        }
    }

    private fun finishActivity() {
        val intent = Intent(this@ExerciseActivityFromExercises, ExercisesActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }

    private fun playAudio() {
        val exercise = exercises[currentExerciseIndex]
        val audioResource = exercise.soundOfExercise
            .substringBefore(".")
            .substringAfter("app/src/main/res/raw/")

        val audioUri = "android.resource://${packageName}/raw/$audioResource"
        mediaPlayer = MediaPlayer.create(this, Uri.parse(audioUri))
        mediaPlayer?.start()
    }

    private fun stopAudio() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun showExercise(index: Int) {
        val exercise = exercises[index]

        textViewExercise.text = "Упражнение ${exercise.id} из ${countExercicesString}"

        textViewTitle.text = exercise.titleOfExercise
        textViewDescription.text = exercise.descriptionOfExercise

        val gifResource = exercise.animationOfExercise
            .substringBefore(".")
            .substringAfter("app/src/main/res/raw/")

        Glide.with(this@ExerciseActivityFromExercises)
            .asGif()
            .load("android.resource://${packageName}/raw/$gifResource")
            .into(gifImageView)

        button.text = "Начать"
        timer = null
        stopAudio()
    }
}
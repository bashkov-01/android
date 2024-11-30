package com.example.android_project

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExerciseActivity : AppCompatActivity() {
    var timer: CountDownTimer? = null
    private lateinit var textViewTitle: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var videoView: VideoView
    private lateinit var button: Button
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        // Инициализация элементов интерфейса
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription = findViewById(R.id.textViewDescription)
        videoView = findViewById(R.id.videoView)
        button = findViewById(R.id.button2)

        // Получаем id занятия, переданный через Intent
        val lessonId = intent.getIntExtra("lesson_id", -1)

        if (lessonId != -1) {
            val exerciseDao = MainDb.getDb(this).exerciseDao()

            // Загружаем упражнения по lessonId
            GlobalScope.launch(Dispatchers.Main) {
                val exercises = exerciseDao.getExercisesByLessonId(lessonId)

                // Если упражнения есть
                if (exercises.isNotEmpty()) {
                    val exercise = exercises[0]  // Выбираем первое упражнение

                    // Отображаем данные об упражнении
                    textViewTitle.text = exercise.titleOfExercise
                    textViewDescription.text = exercise.descriptionOfExercise

//                    // Загружаем видео
//                    val videoFileName = exercise.animationOfExercise.replace("app/src/main/res/raw/", "")

                    // Формируем URI для доступа к ресурсу
                    val videoUri = Uri.parse("/Users/bashkov/AndroidStudioProjects/Android_Project/app/src/main/res/raw/video1.mp4")
                    videoView.setVideoURI(videoUri)
                    videoView.setOnPreparedListener {
                        videoView.start()
                    }

                    videoView.setOnErrorListener { mp, what, extra ->
                        Log.e("VideoError", "Error occurred while playing video: $what, $extra")
                        true  // True means the error is handled
                    }
//
//                    // Загружаем аудио
//                    mediaPlayer = MediaPlayer.create(this@ExerciseActivity, Uri.parse(exercise.soundOfExercise))
//
//                    // Устанавливаем таймер
//                    val timerDuration = exercise.timerOfExercise * 1000L // преобразуем в миллисекунды
//                    startTimer(timerDuration)

//                    // Обработчик нажатия на кнопку
//                    button.setOnClickListener {
//                        // Запускаем видео
//                        videoView.start()
//
//                        // Запускаем таймер
//                        startTimer(timerDuration)
//
//                        // Запускаем аудио
//                        if (!mediaPlayer.isPlaying) {
//                            mediaPlayer.start()
//                        }
//                    }
                }
            }
        }
    }

    fun startTimer(duration: Long) {
        timer?.cancel()  // Отменяем старый таймер, если он есть

        timer = object : CountDownTimer(duration, 1000) {  // Таймер на указанное время
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                button.text = String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                // Когда таймер закончится, можно выполнить какие-либо действия
                button.text = "00:00"
            }
        }
        timer?.start()
    }

    override fun onPause() {
        super.onPause()
        // Останавливаем видео и аудио при выходе из приложения
        if (videoView.isPlaying) {
            videoView.pause()
        }
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
        timer?.cancel()  // Останавливаем таймер
    }

    override fun onDestroy() {
        super.onDestroy()
        // Освобождаем ресурсы
        mediaPlayer.release()
    }
}
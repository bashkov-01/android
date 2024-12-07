package com.example.android_project

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.android_project.models.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExerciseActivity : AppCompatActivity() {
    private var timer: CountDownTimer? = null
    private lateinit var textViewTitle: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var button: Button
    private lateinit var gifImageView: ImageView
    private var lessonId = 0

    private var currentExerciseIndex = 0  // Текущий индекс упражнения
    private var exercises = listOf<Exercise>()  // Список упражнений
    private var isTimerRunning = false // Флаг состояния таймера
    private var mediaPlayer: MediaPlayer? = null
    private var isPaused = false // Флаг паузы
    private var timeLeftInMillis: Long = 0 // Оставшееся время на таймере

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        gifImageView = findViewById(R.id.gifImage)
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription = findViewById(R.id.textViewDescription)
        button = findViewById(R.id.button2)

        // Получаем id занятия, переданный через Intent
        lessonId = intent.getIntExtra("lesson_id", -1)

        if (lessonId != -1) {
            val exerciseDao = MainDb.getDb(this).exerciseDao()

            // Загружаем упражнения по lessonId
            GlobalScope.launch(Dispatchers.Main) {
                exercises = exerciseDao.getExercisesByLessonId(lessonId)

                if (exercises.isNotEmpty()) {
                    showExercise(currentExerciseIndex)

                    button.setOnClickListener {
                        handleButtonClick()
                    }
                }
            }
        }
    }

    //    Этот метод обрабатывает нажатия на кнопку.
    //    В зависимости от состояния таймера выполняется одно из следующих действий:
    //Если таймер запущен и не на паузе, он ставится на паузу.
    //Если таймер на паузе, он возобновляется.
    //Если таймер не был запущен, он запускается для текущего упражнения.
    //Если таймер завершён, переход к следующему упражнению.
    private fun handleButtonClick() {
        when {
            isTimerRunning && !isPaused -> {
                // Поставить на паузу
                pauseExerciseTimer()
            }
            isPaused -> {
                // Возобновить таймер
                resumeExerciseTimer()
            }
            timer == null -> {
                // Запустить таймер впервые
                val time = exercises[currentExerciseIndex].timerOfExercise
                startExerciseTimer(time)
            }
//            else -> {
//                // Если таймер завершён, перейти к следующему упражнению
//                moveToNextExercise()
//            }
        }
    }

    //Останавливает таймер и ставит флаг паузы в true.
    // Меняет текст кнопки на "Продолжить" и останавливает воспроизведение аудио.
    private fun pauseExerciseTimer() {
        timer?.cancel()
        isPaused = true
        isTimerRunning = false
        button.text = "Продолжить"
    }
    //Возобновляет таймер с оставшегося времени, меняет флаг состояния таймера на активный и запускает аудио.
    private fun resumeExerciseTimer() {
        startExerciseTimer(timeLeftInMillis / 1000) // Возобновляем с оставшегося времени
        isPaused = false
        isTimerRunning = true
        stopAudio()

//        playAudio()
    }

    //Запускает новый таймер для упражнения с заданным временем в секундах.
    //В процессе работы таймера каждую секунду обновляется текст на кнопке с оставшимся временем,
    //а по завершению таймер останавливается, текст кнопки меняется на "Далее",
    //и воспроизведение аудио останавливается. После этого происходит переход к следующему упражнению.
    private fun startExerciseTimer(timeInSeconds: Long) {
        timer?.cancel() // Останавливаем предыдущий таймер
        isTimerRunning = true
        isPaused = false
        button.text = "Пауза"

        playAudio()

        timer = object : CountDownTimer(timeInSeconds * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished // Сохраняем оставшееся время
                val secondsLeft = millisUntilFinished / 1000
                button.text = formatTime(secondsLeft.toInt())
            }

            override fun onFinish() {
                button.text = "Далее" // Меняем текст на кнопке
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
                dialog.dismiss() // Закрыть диалог
                moveToNextExercise() // Переход к следующему упражнению
            }
            .setCancelable(false) // Блокировка закрытия окна вне кнопки

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }


    //Форматирует оставшееся время (в секундах) в строку в формате "минуты:секунды", например, "02:30".
    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }

    //Загружает и воспроизводит аудиофайл для текущего упражнения.
    //Аудиофайл указывается в свойствах soundOfExercise объекта Exercise, и его путь преобразуется в URI,
    //чтобы использовать его через MediaPlayer.
    private fun playAudio() {
        val exercise = exercises[currentExerciseIndex]
        val audioResource = exercise.soundOfExercise
            .substringBefore(".")
            .substringAfter("app/src/main/res/raw/")  // Извлекаем имя файла

        val audioUri = "android.resource://${packageName}/raw/$audioResource"
        mediaPlayer = MediaPlayer.create(this, Uri.parse(audioUri))
        mediaPlayer?.start() // Запуск аудио
    }

    //Останавливает воспроизведение аудио и освобождает ресурсы, связанные с MediaPlayer.
    private fun stopAudio() {
        mediaPlayer?.stop()
        mediaPlayer?.release() // Освобождаем ресурсы
        mediaPlayer = null
    }

    //Отображает данные текущего упражнения на экране:
    //Название и описание упражнения.
    //Анимацию в формате GIF, используя библиотеку Glide для загрузки и отображения.
    //Кнопка получает текст "Начать", и таймер сбрасывается.
    private fun showExercise(index: Int) {
        val exercise = exercises[index]
        textViewTitle.text = exercise.titleOfExercise
        textViewDescription.text = exercise.descriptionOfExercise

        val gifResource = exercise.animationOfExercise
            .substringBefore(".")
            .substringAfter("app/src/main/res/raw/")  // Извлекаем имя файла

        Glide.with(this@ExerciseActivity)
            .asGif()
            .load("android.resource://${packageName}/raw/$gifResource")
            .into(gifImageView)

        button.text = "Начать"
        timer = null // Сбрасываем таймер
        stopAudio()
    }

    //Переходит к следующему упражнению в списке.
    //Если упражнение — последнее, активность переключается на экран с занятиями,
    //передавая ID текущего занятия.
    private fun moveToNextExercise() {
        if (currentExerciseIndex < exercises.size - 1) {
            currentExerciseIndex++
            showExercise(currentExerciseIndex)
        } else {
            val intent = Intent(this@ExerciseActivity, LessonActivity::class.java)
            intent.putExtra("lesson_id_exercise", lessonId)  // Передаем id занятия
            startActivity(intent)
        }
    }
}
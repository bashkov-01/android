package com.example.android_project

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.android_project.dao.ExerciseDao
import com.example.android_project.models.Exercise
import com.example.android_project.models.Userr
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserActivity: AppCompatActivity() {

    var isStrobism: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

//        val dbs = MainDb.getDb(applicationContext)
//        val exerciseDao = dbs.exerciseDao()
//
//        insertExercise(exerciseDao)

        val dateOfBirth: TextView = findViewById(R.id.textDateOfBirth)
        val listOfExerciseLoad: Spinner = findViewById(R.id.listExerciseLoad)//Нагрузка упражнения
        val listOfCategory: Spinner = findViewById(R.id.listCategory)//Тип упражнения
        val listOfStrobism: Spinner = findViewById(R.id.listStrobism)//Стробизм
        val listOfCountTime: Spinner = findViewById(R.id.listCountOfTime)//Кол-во времени
        val button: Button = findViewById(R.id.button)
//
        val itemsExerciseLoad = listOf("Компьютер", "Книги", "Телефон")
        val itemsCategory = listOf("Короткие", "Длинные")
        val itemsStrobism = listOf("Есть", "Нет")
        val itemsCountOfTime = listOf("2-4", "4-6", "6-8")

        listOfExerciseLoad.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsExerciseLoad)
        listOfCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsCategory)
        listOfStrobism.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsStrobism)
        listOfCountTime.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsCountOfTime)

//         Обработчик нажатия на кнопку
        button.setOnClickListener {
            // Получаем выбранные значения из Spinner
            val selectedExerciseLoad = listOfExerciseLoad.selectedItem.toString()
            val selectedCategory = listOfCategory.selectedItem.toString()
            val selectedStrobism = listOfStrobism.selectedItem.toString()
            if(selectedStrobism == itemsStrobism.elementAt(0)) { isStrobism = true}
            else isStrobism = false
            val selectedCountTime = listOfCountTime.selectedItem.toString()

            val db = MainDb.getDb(applicationContext)

            val user = Userr(
                dateOfBirth = dateOfBirth.toString(), // Пример даты рождения
                exerciseLoad = selectedExerciseLoad,        // Пример нагрузки
                isStrobism = isStrobism,          // Пример для стробизма
                categoryExercise = selectedCategory,  // Пример категории упражнения
                countOfTime = selectedCountTime          // Пример времени
            )

//             Вставка пользователя в базу данных в фоновом потоке
            lifecycleScope.launch {
//                db.userDao().getAllUsers()
                db.userDao().insertUser(user)
            }

            val intent = Intent(this@UserActivity, LessonActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun insertExercise(exerciseDao: ExerciseDao) {
        CoroutineScope(Dispatchers.IO).launch {
            val exercises = listOf(
                Exercise(
                    titleOfExercise = "Жмурки",
                    descriptionOfExercise = "Крепко закройте глаза на 3-5 секунд. Повторите 6-8 раз.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video1.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio1.mp3",
                    isStrobism = true,
                    exerciseLoad = "Компьютер",
                    exerciseCategory = "Короткие",
                    countOfTime = "2-4",
                    lessonId = 1
                ),
                Exercise(
                    titleOfExercise = "Переносица",
                    descriptionOfExercise = "Смотреть прямо перед собой 2-3 сек. Сводим зрачки к переносице изо всех сил, приблизив палец к носу 3-5 сек. Повторить 10-12 раз.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video2.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio2.mp3",
                    isStrobism = true,
                    exerciseLoad = "Книги",
                    exerciseCategory = "Длинные",
                    countOfTime = "2-4",
                    lessonId = 1
                ),
                Exercise(
                    titleOfExercise = "Пол – потолок - стены",
                    descriptionOfExercise = "Медленно переводите взгляд с пола на потолок, вправо, влево и обратно, не меняя положения головы.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video3.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio3.mp3",
                    isStrobism = true,
                    exerciseLoad = "Телефон",
                    exerciseCategory = "Длинные",
                    countOfTime = "4-6",
                    lessonId = 1
                ),
                Exercise(
                    titleOfExercise = "Шторки",
                    descriptionOfExercise = "Быстро и легко моргайте 2 минуты. Это упражнение способствует улучшению кровообращения.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video4.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio4.mp3",
                    isStrobism = false,
                    exerciseLoad = "Компьютер",
                    exerciseCategory = "Короткие",
                    countOfTime = "6-8",
                    lessonId = 2
                ),
                Exercise(
                    titleOfExercise = "Часики",
                    descriptionOfExercise = "Перемещайте взгляд в разных направлениях: по кругу – по часовой стрелке и против.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video5.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio5.mp3",
                    isStrobism = false,
                    exerciseLoad = "Компьютер",
                    exerciseCategory = "Короткие",
                    countOfTime = "6-8",
                    lessonId = 2
                ),
                Exercise(
                    titleOfExercise = "Стреляем глазками",
                    descriptionOfExercise = "Вверх – влево, вниз – право и наоборот. Упражнение укрепляет мышцы глаз.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video6.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio6.mp3",
                    isStrobism = false,
                    exerciseLoad = "Компьютер",
                    exerciseCategory = "Короткие",
                    countOfTime = "6-8",
                    lessonId = 2
                ),
                Exercise(
                    titleOfExercise = "Удивление",
                    descriptionOfExercise = "Зажмуриваем глаза на пять секунд и широко распахивает веки.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video7.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio7.mp3",
                    isStrobism = true,
                    exerciseLoad = "Компьютер",
                    exerciseCategory = "Короткие",
                    countOfTime = "6-8",
                    lessonId = 3
                ),
                Exercise(
                    titleOfExercise = "Метка на стекле",
                    descriptionOfExercise = "Определяем точку на стекле, затем переводим взгляд на далекий объект.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video8.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio8.mp3",
                    isStrobism = true,
                    exerciseLoad = "Компьютер",
                    exerciseCategory = "Короткие",
                    countOfTime = "6-8",
                    lessonId = 3
                ),
                Exercise(
                    titleOfExercise = "Массаж",
                    descriptionOfExercise = "Тремя пальцами каждой руки легко нажмите на верхние веки.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video9.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio9.mp3",
                    isStrobism = true,
                    exerciseLoad = "Компьютер",
                    exerciseCategory = "Короткие",
                    countOfTime = "6-8",
                    lessonId = 3
                ),
                Exercise(
                    titleOfExercise = "Восьмерка",
                    descriptionOfExercise = "Медленно водите взглядом по контуру восьмерки.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video10.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio10.mp3",
                    isStrobism = false,
                    exerciseLoad = "Компьютер",
                    exerciseCategory = "Короткие",
                    countOfTime = "6-8",
                    lessonId = 4
                ),
                Exercise(
                    titleOfExercise = "Рыбка",
                    descriptionOfExercise = "Попробуйте широко распахнуть глаза, смотря вверх и вниз.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video11.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio11.mp3",
                    isStrobism = false,
                    exerciseLoad = "Телефон",
                    exerciseCategory = "Короткие",
                    countOfTime = "6-8",
                    lessonId = 4
                ),
                Exercise(
                    titleOfExercise = "Моргаем",
                    descriptionOfExercise = "Сильно моргайте, чтобы активировать кровообращение в глазах.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video12.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio12.mp3",
                    isStrobism = true,
                    exerciseLoad = "Телефон",
                    exerciseCategory = "Длинные",
                    countOfTime = "2-4",
                    lessonId = 5
                ),
                Exercise(
                    titleOfExercise = "Лабиринт",
                    descriptionOfExercise = "Следите за точкой в центре лабиринта.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video13.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio13.mp3",
                    isStrobism = true,
                    exerciseLoad = "Телефон",
                    exerciseCategory = "Длинные",
                    countOfTime = "2-4",
                    lessonId = 5
                ),
                Exercise(
                    titleOfExercise = "Вращение глаз",
                    descriptionOfExercise = "Переводим взгляд по кругу, не меняя положения головы.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video14.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio14.mp3",
                    isStrobism = false,
                    exerciseLoad = "Компьютер",
                    exerciseCategory = "Длинные",
                    countOfTime = "6-8",
                    lessonId = 5
                ),
                Exercise(
                    titleOfExercise = "Внимание",
                    descriptionOfExercise = "Сосредоточьтесь на каком-либо объекте в комнате или на экране.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video15.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio15.mp3",
                    isStrobism = true,
                    exerciseLoad = "Компьютер",
                    exerciseCategory = "Длинные",
                    countOfTime = "6-8",
                    lessonId = 6
                ),
                Exercise(
                    titleOfExercise = "Реакция",
                    descriptionOfExercise = "Отводите взгляд влево, вправо, вверх и вниз, сразу же возвращаясь в начальное положение.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video16.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio16.mp3",
                    isStrobism = false,
                    exerciseLoad = "Телефон",
                    exerciseCategory = "Длинные",
                    countOfTime = "6-8",
                    lessonId = 6
                ),
                Exercise(
                    titleOfExercise = "Статика",
                    descriptionOfExercise = "Удерживайте взгляд на одном месте, не двигая головой, 3-5 секунд.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video17.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio17.mp3",
                    isStrobism = true,
                    exerciseLoad = "Телефон",
                    exerciseCategory = "Длинные",
                    countOfTime = "6-8",
                    lessonId = 6
                ),
                Exercise(
                    titleOfExercise = "Фокус",
                    descriptionOfExercise = "Переводите взгляд с близких объектов на дальние.",
                    pictureOfExercise = "",
                    animationOfExercise = "app/src/main/res/raw/video18.gif",
                    timerOfExercise = 60,
                    soundOfExercise = "app/src/main/res/raw/audio18.mp3",
                    isStrobism = true,
                    exerciseLoad = "Телефон",
                    exerciseCategory = "Длинные",
                    countOfTime = "6-8",
                    lessonId = 6
                )
            )
            exerciseDao.insertExercises(exercises)
        }
    }
}

//INSERT INTO Exercise (title, description, picture, animation, timer, sound, isStrobism, exerciseLoad, categoryExercise, countOfTime, lessonId) VALUES
//('Жмурки', 'Крепко закройте глаза на 3-5 секунд. Повторите 6-8 раз.', '', 'app/src/main/res/raw/video1.gif', 60, 'app/src/main/res/raw/audio1.mp3', 1, 'Компьютер', 'Короткие', '2-4', 1),
//('Переносица', 'Смотреть прямо перед собой 2-3 сек. Сводим зрачки к переносице изо всех сил, приблизив палец к носу 3-5 сек. Повторить 10-12 раз.', '', 'app/src/main/res/raw/video2.gif', 60, 'app/src/main/res/raw/audio2.mp3', 1, 'Книги', 'Длинные', '2-4', 1),
//('Пол – потолок - стены', 'Медленно переводите взгляд с пола на потолок, вправо, влево и обратно, не меняя положения головы.', '', 'app/src/main/res/raw/video3.gif', 60, 'app/src/main/res/raw/audio3.mp3', 1, 'Телефон', 'Длинные', '4-6', 1),
//('Шторки', 'Быстро и легко моргайте 2 минуты. Это упражнение способствует улучшению кровообращения.', '', 'app/src/main/res/raw/video4.gif', 60, 'app/src/main/res/raw/audio4.mp3', 0, 'Компьютер', 'Короткие', '6-8', 2),
//('Часики', 'Перемещайте взгляд в разных направлениях: по кругу – по часовой стрелке и против.', '', 'app/src/main/res/raw/video5.gif', 60, 'app/src/main/res/raw/audio5.mp3', 0, 'Компьютер', 'Короткие', '6-8', 2),
//('Стреляем глазками', 'Вверх – влево, вниз – право и наоборот. Упражнение укрепляет мышцы глаз.', '', 'app/src/main/res/raw/video6.gif', 60, 'app/src/main/res/raw/audio6.mp3', 0, 'Компьютер', 'Короткие', '6-8', 2),
//('Удивление', 'Зажмуриваем глаза на пять секунд и широко распахивает веки.', '', 'app/src/main/res/raw/video7.gif', 60, 'app/src/main/res/raw/audio7.mp3', 1, 'Компьютер', 'Короткие', '6-8', 3),
//('Метка на стекле', 'Определяем точку на стекле, затем переводим взгляд на далекий объект.', '', 'app/src/main/res/raw/video8.gif', 60, 'app/src/main/res/raw/audio8.mp3', 1, 'Компьютер', 'Короткие', '6-8', 3),
//('Массаж', 'Тремя пальцами каждой руки легко нажмите на верхние веки.', '', 'app/src/main/res/raw/video9.gif', 60, 'app/src/main/res/raw/audio9.mp3', 1, 'Компьютер', 'Короткие', '6-8', 3),
//('Восьмерка', 'Медленно водите взглядом по контуру восьмерки.', '', 'app/src/main/res/raw/video10.gif', 60, 'app/src/main/res/raw/audio10.mp3', 0, 'Компьютер', 'Короткие', '6-8', 4),
//('Рыбка', 'Попробуйте широко распахнуть глаза, смотря вверх и вниз.', '', 'app/src/main/res/raw/video11.gif', 60, 'app/src/main/res/raw/audio11.mp3', 0, 'Телефон', 'Короткие', '6-8', 4),
//('Моргаем', 'Сильно моргайте, чтобы активировать кровообращение в глазах.', '', 'app/src/main/res/raw/video12.gif', 60, 'app/src/main/res/raw/audio12.mp3', 1, 'Телефон', 'Длинные', '2-4', 5),
//('Лабиринт', 'Следите за точкой в центре лабиринта.', '', 'app/src/main/res/raw/video13.gif', 60, 'app/src/main/res/raw/audio13.mp3', 1, 'Телефон', 'Длинные', '2-4', 5),
//('Вращение глаз', 'Переводим взгляд по кругу, не меняя положения головы.', '', 'app/src/main/res/raw/video14.gif', 60, 'app/src/main/res/raw/audio14.mp3', 0, 'Компьютер', 'Длинные', '6-8', 5),
//('Внимание', 'Сосредоточьтесь на каком-либо объекте в комнате или на экране.', '', 'app/src/main/res/raw/video15.gif', 60, 'app/src/main/res/raw/audio15.mp3', 1, 'Компьютер', 'Длинные', '6-8', 6),
//('Реакция', 'Отводите взгляд влево, вправо, вверх и вниз, сразу же возвращаясь в начальное положение.', '', 'app/src/main/res/raw/video16.gif', 60, 'app/src/main/res/raw/audio16.mp3', 0, 'Телефон', 'Длинные', '6-8', 6),
//('Статика', 'Удерживайте взгляд на одном месте, не двигая головой, 3-5 секунд.', '', 'app/src/main/res/raw/video17.gif', 60, 'app/src/main/res/raw/audio17.mp3', 1, 'Телефон', 'Длинные', '6-8', 6),
//('Фокус', 'Переводите взгляд с близких объектов на дальние.', '', 'app/src/main/res/raw/video18.gif', 60, 'app/src/main/res/raw/audio18.mp3', 1, 'Телефон', 'Длинные', '6-8', 6);
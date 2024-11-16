package com.example.android_project

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.android_project.models.Userr
import kotlinx.coroutines.launch

class UserActivity: AppCompatActivity() {

    var isStrobism: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val dateOfBirth: TextView = findViewById(R.id.textDateOfBirth)
        val listOfExerciseLoad: Spinner = findViewById(R.id.listExerciseLoad)//Нагрузка упражнения
        val listOfCategory: Spinner = findViewById(R.id.listCategory)//Тип упражнения
        val listOfStrobism: Spinner = findViewById(R.id.listStrobism)//Стробизм
        val listOfCountTime: Spinner = findViewById(R.id.listCountOfTime)//Кол-во времени
        val button: Button = findViewById(R.id.button)

        val itemsExerciseLoad = listOf("Компьютер", "Книги", "Телефон")
        val itemsCategory = listOf("Короткие", "Длинные")
        val itemsStrobism = listOf("Есть", "Нет")
        val itemsCountOfTime = listOf("2-4", "4-6", "6-8")

        listOfExerciseLoad.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsExerciseLoad)
        listOfCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsCategory)
        listOfStrobism.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsStrobism)
        listOfCountTime.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsCountOfTime)

        // Обработчик нажатия на кнопку
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

            // Вставка пользователя в базу данных в фоновом потоке
            lifecycleScope.launch {
//                db.userDao().getAllUsers()
                db.userDao().insertUser(user)
            }

            val intent = Intent(this@UserActivity, LessonActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
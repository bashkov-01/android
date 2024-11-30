package com.example.android_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.android_project.dao.ExerciseDao
import com.example.android_project.models.Exercise
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val intent = Intent(this@MainActivity, UserActivity::class.java)
        startActivity(intent)





        // Получаем доступ к базе данных
//        val db = MainDb.getDb(applicationContext)
//        lifecycleScope.launch {
//            // Получаем количество записей в таблице Userr
//            val userCount = db.userDao().getUserCount()
//
//            // Логируем количество записей для проверки
//            println("Количество записей в таблице Userr: $userCount")
//
//            // Если кол-во записей в таблице анкета = 0, то есть нет заполненной анкеты, то переходим на страницу заполнения анкеты
//            val intent = if (userCount == 0) {
//                Intent(this@MainActivity, UserActivity::class.java)
//            } else {
//                Intent(this@MainActivity, LessonActivity::class.java)
//            }
//
//            startActivity(intent)
//            finish()
//        }
//
//        val intent = Intent(this, UserActivity::class.java)
//        startActivity(intent)

    }



}
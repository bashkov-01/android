package com.example.android_project.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise",
    foreignKeys = [
        ForeignKey(
            entity = Lesson::class,
            parentColumns = ["id"],
            childColumns = ["lessonId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "title")
    var titleOfExercise: String,
    @ColumnInfo(name = "description")
    var descriptionOfExercise: String,
    @ColumnInfo(name = "picture")
    var pictureOfExercise: String,
    @ColumnInfo(name = "animation")
    var animationOfExercise: String,
    @ColumnInfo(name = "timer")
    var timerOfExercise: Long,
    @ColumnInfo(name = "sound")
    var soundOfExercise: String,
    @ColumnInfo(name = "isStrobism")
    var isStrobism: Boolean,
    @ColumnInfo(name = "exerciseLoad")
    var exerciseLoad: String,
    @ColumnInfo(name = "categoryExercise")
    var exerciseCategory: String,
    @ColumnInfo(name = "countOfTime")
    var countOfTime: String,
    @ColumnInfo(name = "lessonId")
    var lessonId: Int
)
package com.example.android_project.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson")
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    var id: Int?= null,
    @ColumnInfo(name = "title")
    var titleOfLesson: String,
    @ColumnInfo(name = "description")
    var descriptionOfLesson: String,
)
package com.example.android_project.models
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class Userr(
    @PrimaryKey(autoGenerate = true)
    var id: Int?= null,
    @ColumnInfo(name = "dateOfBirth")
    var dateOfBirth: String,
    @ColumnInfo(name = "exerciseLoad")
    var exerciseLoad: String,
    @ColumnInfo(name = "isStrobism")
    var isStrobism: Boolean,
    @ColumnInfo(name = "categoryExercise")
    var categoryExercise: String,
    @ColumnInfo(name = "countOfTime")
    var countOfTime: String
)
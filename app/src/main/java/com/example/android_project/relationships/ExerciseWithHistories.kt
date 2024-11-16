package com.example.android_project.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.android_project.models.Exercise
import com.example.android_project.models.History

//data class com.example.android_project.relationships.ExerciseWithHistories(
//    @Embedded val exercise: com.example.android_project.models.Exercise,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "exerciseId"
//    )
//    val histories: List<com.example.android_project.models.History>
//)

data class ExerciseWithHistories(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseId"
    )
    val histories: List<History>
)
package com.example.android_project.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.android_project.models.Exercise
import com.example.android_project.models.Lesson

//data class com.example.android_project.relationships.LessonWithExercises(
//    @Embedded val lesson: com.example.android_project.models.Lesson,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "lessonId"
//    )
//    val exercises: List<com.example.android_project.models.Exercise>
//)

data class LessonWithExercises(
    @Embedded val lesson: Lesson,
    @Relation(
        parentColumn = "id",
        entityColumn = "lessonId"
    )
    val exercises: List<Exercise>
)
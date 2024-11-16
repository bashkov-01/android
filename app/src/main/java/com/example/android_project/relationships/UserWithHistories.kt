package com.example.android_project.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.android_project.models.History
import com.example.android_project.models.Userr

//data class UserWithUserActivities(
//    @Embedded val user: User,
//    @Relation(
//        parentColumn = "id",  // id из таблицы User
//        entityColumn = "exerciseId"  // Здесь связываем с колонкой exerciseId в таблице com.example.android_project.models.History
//    )
//    val histories: List<com.example.android_project.models.History>
//)


data class UserWithHistories(
    @Embedded val user: Userr,
    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseId"
    )
    val histories: List<History>
)
package com.example.android_project.dao
import androidx.room.*
import com.example.android_project.models.Userr
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<Userr>>

    @Insert
    suspend fun insertUser(user: Userr)

    @Query("SELECT COUNT(*) FROM user")
    suspend fun getUserCount(): Int
}
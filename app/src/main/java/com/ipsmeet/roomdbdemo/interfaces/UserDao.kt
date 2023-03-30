package com.ipsmeet.roomdbdemo.interfaces

import androidx.room.*
import com.ipsmeet.roomdbdemo.dataclass.User

@Dao
interface UserDao {

    @Query("SELECT * FROM UserData")
    fun getAllUser(): List<User>

    @Query("SELECT * FROM UserData WHERE age > 50")
    fun getAgedUser(): List<User>

    @Insert
    suspend fun insertData(user: User)

    @Update
    suspend fun updateData(user: User)

    @Delete
    suspend fun deleteUser(user: User)

}
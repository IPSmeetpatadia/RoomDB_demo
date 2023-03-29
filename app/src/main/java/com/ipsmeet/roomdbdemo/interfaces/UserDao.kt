package com.ipsmeet.roomdbdemo.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ipsmeet.roomdbdemo.dataclass.User

@Dao
interface UserDao {

    @Query("SELECT * FROM UserData")
    fun getAllUser(): List<User>

    @Query("SELECT * FROM UserData WHERE age > 50")
    fun getAgedUser(): List<User>

    /*
    @Query("SELECT * FROM UserData")
    fun getAllUserInfo(): LiveData<List<User>>
    */

    @Insert
    suspend fun insertData(user: User)

    @Delete
    suspend fun deleteUser(user: User)

}
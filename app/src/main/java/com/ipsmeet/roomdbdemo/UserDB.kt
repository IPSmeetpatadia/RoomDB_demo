package com.ipsmeet.roomdbdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ipsmeet.roomdbdemo.dataclass.User
import com.ipsmeet.roomdbdemo.interfaces.UserDao

@Database(entities = [User::class], version = 1)
abstract class UserDB: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDB? = null

        fun getDatabase(context: Context): UserDB {
            return INSTANCE?: synchronized(this) {
                return Room.databaseBuilder(context, UserDB::class.java, "UserData")
                    .allowMainThreadQueries()
                    .build()
            }
        }
    }
}
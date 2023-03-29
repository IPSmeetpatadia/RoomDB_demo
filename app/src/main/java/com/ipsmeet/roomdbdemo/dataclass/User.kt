package com.ipsmeet.roomdbdemo.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserData")
data class User (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "dob") val dob: String
)

package com.ipsmeet.roomdbdemo

import android.app.Application

class UserApplication: Application() {
    val database: UserDB by lazy { UserDB.getDatabase(this) }
}
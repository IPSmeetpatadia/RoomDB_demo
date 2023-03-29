package com.ipsmeet.roomdbdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ipsmeet.roomdbdemo.UserDB
import com.ipsmeet.roomdbdemo.dataclass.User
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    private var _allUsers: MutableLiveData<List<User>> = MutableLiveData()
    private var _agedUsers: MutableLiveData<List<User>> = MutableLiveData()

    fun getAllUsersObservers(): MutableLiveData<List<User>> {
        return _allUsers
    }

    fun getAgedUsersObservers(): MutableLiveData<List<User>> {
        return _agedUsers
    }

    private fun getAllUser() {
        viewModelScope.launch {
            val userDao = UserDB.getDatabase(getApplication()).userDao()
            val list = userDao.getAllUser()
            _allUsers.value = list
        }
    }

    private fun getAgedUser() {
        viewModelScope.launch {
            val userDao = UserDB.getDatabase(getApplication()).userDao()
            val list = userDao.getAgedUser()
            _agedUsers.value = list
        }
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            val userDao = UserDB.getDatabase(getApplication()).userDao()
            userDao.insertData(user)
            getAllUser()
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            val userDao = UserDB.getDatabase(getApplication()).userDao()
            userDao.deleteUser(user)
            getAllUser()
        }
    }
}
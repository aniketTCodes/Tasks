package com.example.tasks

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksViewModel(application: Application):AndroidViewModel(application){
    private val database=Room.databaseBuilder(application,TasksDatabase::class.java,"database").build()

     val repository:TasksRepository = TasksRepository(database.tasksDao())
     val allTask=repository.taskList

    fun addTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

    fun isEmpty():Boolean{
        var res:Boolean=false
        viewModelScope.launch(Dispatchers.IO) { res=repository.isEmpty()  }
        return res
    }

    fun update(done:Boolean,id:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(done,id)
        }
    }





}


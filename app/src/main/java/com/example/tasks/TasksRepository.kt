package com.example.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class TasksRepository(private val dao: TasksDao) {
     val taskList:LiveData<List<Task>> = dao.getAll()

    suspend fun addTask(task: Task){dao.insert(task)}
    suspend fun deleteTask(task: Task)=dao.delete(task)
    suspend fun isEmpty():Boolean{
       return dao.isEmpty()
    }

    suspend fun update(done:Boolean,id:String){
        dao.update(done,id)
    }
}
package com.example.tasks

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class Task(
    @PrimaryKey val id:String,
    val task:String,
    val done:Boolean=false
)

@Dao
interface TasksDao{
    @Query("Select * From Task")
    fun getAll():LiveData<List<Task>>

    @Insert
    fun insert(vararg task:Task)

    @Delete
    fun delete(vararg task:Task)
    @Query("update Task set done=:don where id=:id ")
    fun update (don:Boolean,id:String)

    @Query("SELECT (SELECT COUNT(*) FROM Task) == 0")
    fun isEmpty(): Boolean

}

@Database(entities = [Task::class],version=1)
abstract class TasksDatabase:RoomDatabase(){
    abstract fun tasksDao():TasksDao
}
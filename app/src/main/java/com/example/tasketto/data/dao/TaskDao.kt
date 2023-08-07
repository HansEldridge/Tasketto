package com.example.tasketto.data.dao

import androidx.room.*
import com.example.tasketto.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_item ORDER BY id ASC")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task_item WHERE id = :id")
    suspend fun getTask(id: Int): Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}
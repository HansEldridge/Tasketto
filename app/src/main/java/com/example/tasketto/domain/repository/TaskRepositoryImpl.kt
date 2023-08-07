package com.example.tasketto.domain.repository

import com.example.tasketto.data.dao.TaskDao
import com.example.tasketto.data.repository.TaskRepository
import com.example.tasketto.domain.model.Task


class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {
    override fun getTasks() = taskDao.getTasks()

    override suspend fun getTask(id: Int) = taskDao.getTask(id)

    override suspend fun addTask(task: Task) = taskDao.addTask(task)

    override suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
}
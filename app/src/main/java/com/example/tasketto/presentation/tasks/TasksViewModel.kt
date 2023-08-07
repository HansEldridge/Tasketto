package com.example.tasketto.presentation.tasks

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasketto.domain.model.Task
import com.example.tasketto.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repo: TaskRepository
) : ViewModel() {
    var task by mutableStateOf(Task(0, "", "", Date(0), false))
        private set
    var openCompleteDialog by mutableStateOf(false)
    var openDatePicker by mutableStateOf(false)
    var openDateShow by mutableStateOf(false)

    val tasks = repo.getTasks()

    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat("dd MMMM yyyy")

    fun getTask(id: Int) = viewModelScope.launch {
        task = repo.getTask(id)
    }

    fun addTask(task: Task) = viewModelScope.launch {
        repo.addTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repo.updateTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        repo.deleteTask(task)
    }

    fun openCompleteDialog() {
        openCompleteDialog = true
    }

    fun closeCompleteDialog() {
        openCompleteDialog = false
    }

    fun openDatePicker() {
        openDatePicker = true
    }

    fun closeDatePicker() {
        openDatePicker = false
    }

    fun openDateShow() {
        openDateShow = true
    }

    fun closeDateShow() {
        openDateShow = false
    }
}
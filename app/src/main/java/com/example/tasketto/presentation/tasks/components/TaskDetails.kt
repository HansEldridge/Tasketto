package com.example.tasketto.presentation.tasks.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tasketto.domain.model.Task
import com.example.tasketto.presentation.tasks.TasksViewModel
import kotlinx.coroutines.launch
import java.util.Date

@SuppressLint("UnrememberedMutableState", "SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetails(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    task: Task = viewModel.task,
    addTask: (task: Task) -> Unit,
    taskId: Int
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(Date()) }

    if (taskId != 0) {
        LaunchedEffect(Unit) {
            viewModel.getTask(taskId)
        }
        title = task.title
        description = task.description
        date = task.date
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar (
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    TextButton (
                        modifier = modifier.padding(horizontal = 20.dp),
                        onClick = {
                            if (title.isBlank()) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Need choose a title"
                                    )
                                }
                            } else {
                                val taskAdded =
                                    Task(id = taskId, title = title, description = description, date = date, complete = false)
                                addTask(taskAdded)
                                navigateBack()
                            }
                        }
                    ) {
                        Text(text = "Save task")
                    }
                }
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = modifier.padding(padding)
            ) {
                item {
                    // Title text field
                    TextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp,
                                vertical = 10.dp
                            ),
                        label = { Text(text = "Title") },
                        value = title,
                        onValueChange = {
                            title = it
                        }
                    )
                    // Description text field
                    TextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp,
                                vertical = 10.dp
                            ),
                        label = { Text(text = "Description") },
                        value = description,
                        onValueChange = {
                            description = it
                        }
                    )

                    // Date picker button
                    Card(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Row(
                            modifier = modifier.align(Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = viewModel.dateFormat.format(date), modifier = modifier.padding())
                            Spacer(modifier = modifier.padding(18.dp))
                            Button(
                                modifier = modifier
                                    .padding(vertical = 10.dp),
                                onClick = { viewModel.openDatePicker() }
                            ) {
                                Text(text = "Select a date")
                            }
                        }
                    }

                    // Date picker
                    if (viewModel.openDatePicker) {
                        val datePickerState = rememberDatePickerState()
                        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
                        DatePickerDialog(
                            onDismissRequest = { viewModel.closeDatePicker() },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        viewModel.closeDatePicker()
                                        date = Date(datePickerState.selectedDateMillis ?: 0)
                                    },
                                    enabled = confirmEnabled.value
                                ) {
                                    Text("OK")
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = { viewModel.closeDatePicker() }
                                ) {
                                    Text("Cancel")
                                }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }

                    Spacer(modifier = modifier.padding(bottom = 350.dp))
                }
            }
        }
    )
}
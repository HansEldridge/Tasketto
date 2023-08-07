package com.example.tasketto.presentation.tasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tasketto.R
import com.example.tasketto.presentation.tasks.components.CompletedTasks
import com.example.tasketto.presentation.tasks.components.TaskCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel(),
    navigateToTaskDetails: (taskId: Int) -> Unit
) {
    val tasks by viewModel.tasks.collectAsState(
        initial = emptyList()
    )
    val taskNotCompleted = tasks.filter { !it.complete }
    val taskCompleted = tasks.filter { it.complete }

    Scaffold(
        topBar = {
            TopAppBar (
                title = {
                    Row {
                        Text(
                            text = "Tasks"
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = { viewModel.openCompleteDialog() },
                        enabled = taskCompleted.isNotEmpty(),
                        modifier = modifier
                            .padding(end = 5.dp)
                    ) {
                        Text(
                            text = "Completed"
                        )
                    }

                    FilledIconButton(
                        onClick = { navigateToTaskDetails(0) },
                        modifier = modifier
                            .padding(end = 5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        content = { padding ->
            if (taskNotCompleted.isNotEmpty()) {
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    items(taskNotCompleted) { task ->
                        TaskCard(
                            task = task,
                            deleteTask = { viewModel.deleteTask(task)},
                            navigateToTaskDetails = navigateToTaskDetails
                        )
                    }
                }
            } else {
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_inbox_24),
                        contentDescription = null
                    )
                    Text(text = "List is empty", modifier = modifier.padding(top = 10.dp))
                }
            }
            CompletedTasks()
        }
    )
}
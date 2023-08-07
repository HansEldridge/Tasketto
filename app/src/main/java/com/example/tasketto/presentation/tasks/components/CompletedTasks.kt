package com.example.tasketto.presentation.tasks.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tasketto.R
import com.example.tasketto.presentation.tasks.TasksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedTasks(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val tasks by viewModel.tasks.collectAsState(
        initial = emptyList()
    )
    val taskCompleted = tasks.filter { it.complete }

    if (viewModel.openCompleteDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.closeCompleteDialog()
            },
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 40.dp)
        ) {
            Surface(
                modifier = Modifier,
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column {
                    IconButton(
                        onClick = { viewModel.closeCompleteDialog() },
                        modifier = modifier.padding(top = 10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                    if (taskCompleted.isNotEmpty()) {
                        LazyColumn (
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(taskCompleted) { task ->
                                TaskCardCompleteList(
                                    task = task,
                                    deleteTask = { viewModel.deleteTask(task)}
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
                        }
                        viewModel.closeCompleteDialog()
                    }
                }
            }
        }

    }
}
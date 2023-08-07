package com.example.tasketto.presentation.tasks.components

import androidx.compose.material3.TextButton

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tasketto.domain.model.Task
import com.example.tasketto.presentation.tasks.TasksViewModel

@Composable
fun TaskCardCompleteList(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel(),
    task: Task,
    deleteTask: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .padding(
                horizontal = 15.dp,
                vertical = 10.dp
            )
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(
                modifier = modifier
                    .width(10.dp)
            )
        }
        Divider(thickness = 1.dp)
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = { viewModel.updateTask(task.copy(complete = false)) }) {
                Text(text = "Restore")
            }
            TextButton(onClick = deleteTask) {
                Text(text = "Delete task")
            }
        }
    }
}

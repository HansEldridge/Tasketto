package com.example.tasketto.presentation.tasks.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tasketto.domain.model.Task
import com.example.tasketto.presentation.tasks.TasksViewModel

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel(),
    task: Task,
    deleteTask: () -> Unit,
    navigateToTaskDetails: (bookId: Int) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .padding(
                horizontal = 15.dp,
                vertical = 10.dp
            )
            .fillMaxWidth()
            .clickable {
                navigateToTaskDetails(task.id)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = modifier.weight(3f)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier.padding(bottom = 5.dp)
                )
                if (task.description.isNotEmpty()) {
                    Text(
                        text = task.description,
                        modifier = modifier.padding(bottom = 5.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 5
                    )
                }
                Divider(thickness = 1.dp, modifier = modifier.padding(vertical = 5.dp))
                AssistChip(
                    onClick = { TODO() },
                    label = { Text(text = viewModel.dateFormat.format(task.date)) }
                    )
            }
            Spacer(
                modifier = modifier
                    .width(30.dp)
            )
            FilledIconToggleButton(
                checked = task.complete,
                onCheckedChange = { complete ->
                    viewModel.updateTask(task.copy(complete = complete))
                }
            ) {
                if (task.complete) {
                    Icon(Icons.Filled.CheckCircle, contentDescription = null)
                } else {
                    Icon(Icons.Outlined.CheckCircle, contentDescription = null)
                }
            }
        }
    }
}


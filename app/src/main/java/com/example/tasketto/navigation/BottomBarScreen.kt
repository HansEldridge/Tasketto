package com.example.tasketto.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.tasketto.R

sealed class BottomBarScreen(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    object TasksScreen: BottomBarScreen(
        route = "tasks_screen",
        title = R.string.tasks_screen,
        icon = Icons.Default.List
    )

    object NotesScreen: BottomBarScreen(
        route = "notes_screen",
        title = R.string.notes_screen,
        icon = Icons.Default.Create
    )
}
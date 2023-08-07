package com.example.tasketto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.TASKS
    ) {
        composable(route = Graph.TASKS) {
            HomeScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val TASKS = "tasks_graph"
    const val TASK_DETAILS = "task_details_graph"
    const val NOTES = "notes_graph"
    const val NOTES_DETAILS = "notes_details_graph"
}
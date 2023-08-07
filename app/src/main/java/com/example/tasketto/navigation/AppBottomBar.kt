package com.example.tasketto.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tasketto.presentation.notes.NotesScreen
import com.example.tasketto.presentation.tasks.components.TaskDetails
import com.example.tasketto.presentation.tasks.TasksScreen
import com.example.tasketto.presentation.tasks.TasksViewModel

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: TasksViewModel = hiltViewModel()
) {
    Scaffold(
        bottomBar = {
            val screens = listOf(
                BottomBarScreen.TasksScreen,
                BottomBarScreen.NotesScreen
            )

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val bottomBarDestination = screens.any { it.route == currentDestination?.route }
            if (bottomBarDestination) {
                NavigationBar {
                    screens.forEach { screen ->
                        NavigationBarItem(
                            label = {
                                Text(text = stringResource(screen.title))
                            },
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = "Navigation Icon"
                                )
                            },
                            selected = currentDestination?.hierarchy?.any {
                                it.route == screen.route
                            } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            NavHost(
                navController = navController,
                route = Graph.TASKS,
                startDestination = BottomBarScreen.TasksScreen.route,
            ) {
                composable(route = BottomBarScreen.TasksScreen.route) {
                    TasksScreen(
                        navigateToTaskDetails = { taskId ->
                            navController.navigate(
                                route = "${Graph.TASK_DETAILS}/${taskId}"
                            )
                        }
                    )
                }
                composable(route = BottomBarScreen.NotesScreen.route) {
                    NotesScreen(

                    )
                }
                composable(
                    route = "${Graph.TASK_DETAILS}/{taskId}",
                    arguments = listOf(
                        navArgument("taskId") {
                            type = NavType.IntType
                        }
                    )
                ) { backStackEntry ->
                    val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
                    TaskDetails(
                        navigateBack = { navController.popBackStack() },
                        addTask = { task ->
                            viewModel.addTask(task)
                        },
                        taskId = taskId
                    )
                }
            }
        }
    }
}
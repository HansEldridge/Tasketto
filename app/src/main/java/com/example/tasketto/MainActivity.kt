package com.example.tasketto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.tasketto.navigation.RootNavigationGraph
import com.example.tasketto.ui.theme.TaskettoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskettoTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}
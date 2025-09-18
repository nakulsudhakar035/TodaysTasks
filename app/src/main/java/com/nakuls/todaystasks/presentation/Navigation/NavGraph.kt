package com.nakuls.todaystasks.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nakuls.todaystasks.presentation.task.AddTaskDialog
import com.nakuls.todaystasks.presentation.task.EmptyScreen
import com.nakuls.todaystasks.presentation.task.ErrorScreen
import com.nakuls.todaystasks.presentation.task.LoadingScreen
import com.nakuls.todaystasks.presentation.task.TaskListScreen
import com.nakuls.todaystasks.presentation.task.TaskViewModel
import com.nakuls.todaystasks.presentation.task.TasksUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val viewModel: TaskViewModel = koinViewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.TASK_LIST
    ) {
        composable(Routes.TASK_LIST) {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            when (val state = uiState) {
                is TasksUiState.Loading -> LoadingScreen()
                is TasksUiState.Success -> TaskListScreen(
                    tasks = state.tasks,
                    onAddTask = { title, description ->
                        viewModel.addTask(title, description)
                    },
                    onToggleComplete = viewModel::toggleTaskCompletion,
                    onDelete = viewModel::deleteTask
                )

                is TasksUiState.Error -> ErrorScreen(
                    message = state.message,
                    onRetry = viewModel::retry
                )

                TasksUiState.Empty -> EmptyScreen(
                    onAddTask = {
                        navController.navigate(Routes.ADD_TASK)
                    }
                )
            }

        }

        composable(Routes.ADD_TASK) {
            AddTaskDialog (
                onAddTask = { title, description ->
                    viewModel.addTask(title, description)
                    navController.popBackStack()
                },
                onDismiss = { navController.popBackStack() }
            )
        }
    }
}
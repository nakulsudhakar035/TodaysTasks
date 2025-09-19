package com.nakuls.wearos_app.presentation

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun TaskListScreen(
    onAddTask: () -> Unit,
    viewModel: WearTasksViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    when (uiState) {
        is WearTasksUiState.Loading -> TextScreen(message = "Loading tasks...")
        is WearTasksUiState.Success -> TaskListContent(
            tasks = uiState.tasks,
            onToggleTask = viewModel::toggleTaskCompletion,
            onAddTask = onAddTask
        )
        is WearTasksUiState.Empty -> TextScreen(message = "No tasks")
        is WearTasksUiState.Error -> TextScreen(message = uiState.message)
    }
}
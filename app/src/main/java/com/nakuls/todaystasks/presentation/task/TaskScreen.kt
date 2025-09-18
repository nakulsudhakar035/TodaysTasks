package com.nakuls.todaystasks.presentation.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nakuls.todaystasks.domain.model.Task

@Composable
fun TasksScreen(viewModel: TaskViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is TasksUiState.Loading -> {
            LoadingScreen()
        }
        is TasksUiState.Success -> {
            TaskListScreen(
                tasks = state.tasks,
                onAddTask = viewModel::addTask,
                onToggleComplete = viewModel::toggleTaskCompletion,
                onDelete = viewModel::deleteTask
            )
        }
        is TasksUiState.Error -> {
            ErrorScreen(
                message = state.message,
                onRetry = viewModel::retry
            )
        }
        TasksUiState.Empty -> {
            EmptyScreen(onAddTask = viewModel::addTask)
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Something went wrong", color = MaterialTheme.colorScheme.error)
        Text(message, modifier = Modifier.padding(16.dp))
        Button(onClick = onRetry) {
            Text("Try Again")
        }
    }
}

@Composable
fun EmptyScreen(onAddTask: (String, String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("No tasks yet", style = MaterialTheme.typography.bodyLarge)
        Button(
            onClick = { onAddTask("New Task", "") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Create Your First Task")
        }
    }
}
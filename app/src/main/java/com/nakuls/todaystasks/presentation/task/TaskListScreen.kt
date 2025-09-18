package com.nakuls.todaystasks.presentation.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakuls.todaystasks.domain.model.Task
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskListScreen(
    tasks: List<Task>,
    onAddTask: (String, String) -> Unit,
    onToggleComplete: (Task) -> Unit,
    onDelete: (Task) -> Unit
) {

    var showAddDialog by remember { mutableStateOf(false) }

    Column {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tasks (${tasks.size})",
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add task")
            }
        }

        // Task List
        LazyColumn() {
            itemsIndexed(
                items = tasks,
                key = { index, task -> "$index-${task.id}" }
            ) { index, task ->
                TaskItem(
                    task = task,
                    onToggleComplete = { onToggleComplete(task) },
                    onDelete = { onDelete(task) }
                )
            }
        }
    }

    if (showAddDialog) {
        AddTaskDialog(
            onDismiss = { showAddDialog = false },
            onAddTask = { title, description ->
                onAddTask(title, description)
                showAddDialog = false
            }
        )
    }
}


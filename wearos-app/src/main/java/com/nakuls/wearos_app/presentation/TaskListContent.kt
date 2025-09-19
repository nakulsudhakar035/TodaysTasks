package com.nakuls.wearos_app.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Text
import com.nakuls.wearos_app.domain.model.Task

@Composable
fun TaskListContent(
    tasks: List<Task>,
    onToggleTask: (Long) -> Unit,
    onAddTask: () -> Unit
) {
    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text("My Tasks")
        }

        items(tasks.size) { index ->
            val task = tasks[index]
            TaskItem(
                task = task,
                onToggleComplete = { onToggleTask(task.id ?: 0L) }
            )
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun TaskListScreenPreview() {
    TaskListScreen(onAddTask = {})
}
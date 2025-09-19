package com.nakuls.wearos_app.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.nakuls.wearos_app.domain.model.Task

@Composable
fun TaskItem(
    task: Task,
    onToggleComplete: () -> Unit
) {
    Chip(
        onClick = onToggleComplete,
        colors = ChipDefaults.chipColors(),
        icon = {
            Icon(
                imageVector = if (task.isCompleted) {
                    Icons.Default.Check
                } else {
                    Icons.Default.Clear
                },
                contentDescription = if (task.isCompleted) "Completed" else "Incomplete"
            )
        },
        label = {
            Text(text = task.title)
        }
    )
}

@Preview
@Composable
fun TaskItemPreview() {
    TaskItem(
        task = Task.createNew(title = "Sample Task", isCompleted = true),
        onToggleComplete = {}
    )
}
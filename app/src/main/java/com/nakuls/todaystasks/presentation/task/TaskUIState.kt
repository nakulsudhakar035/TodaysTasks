package com.nakuls.todaystasks.presentation.task

import com.nakuls.todaystasks.domain.model.Task

sealed interface TasksUiState {
    data object Loading : TasksUiState
    data class Success(val tasks: List<Task>) : TasksUiState
    data class Error(val message: String) : TasksUiState
    data object Empty : TasksUiState
}

package com.nakuls.wearos_app.presentation

import com.nakuls.wearos_app.domain.model.Task

sealed interface WearTasksUiState {
    data class Loading(val message: String) : WearTasksUiState
    data class Success(val tasks: List<Task>) : WearTasksUiState
    data class Empty(val message: String) : WearTasksUiState
    data class Error(val message: String) : WearTasksUiState
}
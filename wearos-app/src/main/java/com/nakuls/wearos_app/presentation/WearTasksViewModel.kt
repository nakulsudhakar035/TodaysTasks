package com.nakuls.wearos_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakuls.wearos_app.data.sync.WatchSyncService
import com.nakuls.wearos_app.domain.model.Task
import com.nakuls.wearos_app.domain.usecase.GetTasksUseCase
import com.nakuls.wearos_app.domain.usecase.InsertTaskUseCase
import com.nakuls.wearos_app.domain.usecase.ToggleTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WearTasksViewModel(
    private val getTasksUseCase: GetTasksUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val toggleTaskUseCase: ToggleTaskUseCase,
    private val syncService: WatchSyncService
) : ViewModel() {

    private val _uiState = MutableStateFlow<WearTasksUiState>(WearTasksUiState.Loading("Loading tasks ..."))
    val uiState: StateFlow<WearTasksUiState> = _uiState


    init {
        syncService.startListeningForUpdates()
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            getTasksUseCase().collect { tasks ->
                _uiState.value = when {
                    tasks.isEmpty() -> WearTasksUiState.Empty("No tasks")
                    else -> WearTasksUiState.Success(tasks)
                }
            }
        }
    }

    fun toggleTaskCompletion(taskId: Long) {
        viewModelScope.launch {
            toggleTaskUseCase(taskId)
        }
    }
}


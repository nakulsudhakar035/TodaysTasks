package com.nakuls.todaystasks.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakuls.todaystasks.domain.model.Task
import com.nakuls.todaystasks.domain.usecase.AddTaskUseCase
import com.nakuls.todaystasks.domain.usecase.DeleteTaskUseCase
import com.nakuls.todaystasks.domain.usecase.GetTasksUseCase
import com.nakuls.todaystasks.domain.usecase.UpdateTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel (
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<TasksUiState>(TasksUiState.Loading)
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    init {
        getTasks()
    }

    fun getTasks(){
        viewModelScope.launch {
            getTasksUseCase().collect { tasksList ->
                _uiState.value = when {
                    tasksList.isEmpty() -> TasksUiState.Empty
                    else -> TasksUiState.Success(tasksList)
                }
            }
        }
    }

    fun addTask(title: String, description: String = ""){
        viewModelScope.launch {
            try {
                addTaskUseCase(Task(
                    title = title, description = description
                ))
            } catch (e: Exception) {
                _uiState.value = TasksUiState.Error("Failed to add task: ${e.message}")
            }
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task.copy(isCompleted = !task.isCompleted))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }

    fun retry() {
        _uiState.value = TasksUiState.Loading
        getTasks()
    }
}
package com.nakuls.todaystasks.presentation.task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakuls.todaystasks.data.sync.PhoneSyncService
import com.nakuls.todaystasks.domain.model.Task
import com.nakuls.todaystasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PhoneTasksViewModel(
    private val syncService: PhoneSyncService,
    private val repository: TaskRepository
) : ViewModel() {

    private var currentTasks: List<Task> = emptyList()

    init {
        Log.d("Weather-ViewModelDebug", "PhoneTasksViewModel INIT block called")
        Log.d("Weather-PhoneSync", "📱 Phone ViewModel initializeds")

        // Listen for task changes and auto-sync
        viewModelScope.launch {
            repository.getTasks().collect { tasks ->
                Log.d("Weather-PhoneSync", "Tasks updated: ${tasks.size} tasks")
                currentTasks = tasks
                syncTasksWithWatch() // Auto-sync when tasks change
            }
        }
    }

    fun syncTasksWithWatch() {
        Log.d("Weather-PhoneSync", "Starting sync with watch")
        viewModelScope.launch {
            try {
                if (currentTasks.isEmpty()) {
                    // If we haven't collected tasks yet, get them
                    val tasks = repository.getTasks().first()
                    syncService.syncAllTasksToWatch(tasks)
                } else {
                    syncService.syncAllTasksToWatch(currentTasks)
                }
            } catch (e: Exception) {
                Log.d("Weather-PhoneSync", "Sync failed", e)
            }
        }
    }

    // Call this when tasks are loaded or changed
    fun onTasksUpdated(tasks: List<Task>) {
        viewModelScope.launch {
            syncService.syncAllTasksToWatch(tasks)
        }
    }
}
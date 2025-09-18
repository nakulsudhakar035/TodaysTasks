package com.nakuls.todaystasks.domain.usecase

import com.nakuls.todaystasks.domain.model.Task
import com.nakuls.todaystasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(): Flow<List<Task>> = repository.getTasks()
}
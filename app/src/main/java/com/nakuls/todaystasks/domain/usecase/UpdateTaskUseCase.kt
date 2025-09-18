package com.nakuls.todaystasks.domain.usecase

import com.nakuls.todaystasks.domain.model.Task
import com.nakuls.todaystasks.domain.repository.TaskRepository

class UpdateTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) = repository.updateTask(task)
}
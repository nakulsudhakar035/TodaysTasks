package com.nakuls.wearos_app.domain.usecase

import com.nakuls.wearos_app.data.repository.TaskRepositoryImpl
import com.nakuls.wearos_app.domain.model.Task

class ToggleTaskUseCase(
    private val repository: TaskRepositoryImpl
) {
    suspend operator fun invoke(taskID: Long) {
        return repository.toggleTaskCompletion(taskID)
    }
}
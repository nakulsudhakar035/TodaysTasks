package com.nakuls.wearos_app.domain.usecase

import com.nakuls.wearos_app.data.repository.TaskRepositoryImpl
import com.nakuls.wearos_app.domain.model.Task
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase(
    private val repository: TaskRepositoryImpl
) {
    operator fun invoke(): Flow<List<Task>> {
        return repository.getTasks()
    }
}
package com.nakuls.wearos_app.domain.usecase

import com.nakuls.wearos_app.data.repository.TaskRepositoryImpl
import com.nakuls.wearos_app.domain.model.Task

class InsertTaskUseCase (
    private val repository: TaskRepositoryImpl
) {
    suspend operator fun invoke(task: Task): Long {
        return repository.insertTask(task)
    }
}
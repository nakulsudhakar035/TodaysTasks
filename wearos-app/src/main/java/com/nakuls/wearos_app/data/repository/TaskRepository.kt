package com.nakuls.wearos_app.data.repository

import com.nakuls.wearos_app.data.local.entity.toTask
import com.nakuls.wearos_app.data.local.entity.toTaskEntity
import com.nakuls.wearos_app.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface TaskRepository {

    fun getTasks(): Flow<List<Task>>

    suspend fun getTaskById(id: Long): Task?

    suspend fun saveAllTasks(tasks: List<Task>)

    suspend fun insertTask(task: Task): Long

    suspend fun toggleTaskCompletion(taskId: Long)

}
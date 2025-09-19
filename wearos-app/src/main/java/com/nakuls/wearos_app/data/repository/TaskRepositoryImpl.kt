package com.nakuls.wearos_app.data.repository

import com.nakuls.wearos_app.data.local.dao.TaskDao
import com.nakuls.wearos_app.data.local.entity.toTask
import com.nakuls.wearos_app.data.local.entity.toTaskEntity
import com.nakuls.wearos_app.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(private val taskDao: TaskDao) {
    fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks().map { entities -> entities.map { it.toTask() } }
    }

    suspend fun getTaskById(id: Long): Task? {
        return taskDao.getTasks().first().find { it.id == id }?.toTask()
    }

    suspend fun insertTask(task: Task): Long {
        return taskDao.insertTask(task.toTaskEntity())
    }

    suspend fun toggleTaskCompletion(taskId: Long) {
        val task = getTaskById(taskId)
        task?.let {
            taskDao.updateTaskCompletion(taskId, !it.isCompleted)
        }
    }
}
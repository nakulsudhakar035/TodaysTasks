package com.nakuls.wearos_app.data.repository

import com.nakuls.wearos_app.data.local.dao.TaskDao
import com.nakuls.wearos_app.data.local.entity.TaskEntity
import com.nakuls.wearos_app.data.local.entity.toTask
import com.nakuls.wearos_app.data.local.entity.toTaskEntity
import com.nakuls.wearos_app.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(private val taskDao: TaskDao):TaskRepository {
    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks().map { entities -> entities.map { it.toTask() } }
    }

    override suspend fun getTaskById(id: Long): Task? {
        return taskDao.getTasks().first().find { it.id == id }?.toTask()
    }

    override suspend fun saveAllTasks(tasks: List<Task>) {
        val taskEntities = tasks.map { task ->
            TaskEntity(
                id = task.id,
                title = task.title,
                isCompleted = task.isCompleted,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        }
        taskDao.insertAllTasks(taskEntities)
    }

    override suspend fun insertTask(task: Task): Long {
        return taskDao.insertTask(task.toTaskEntity())
    }

    override suspend fun toggleTaskCompletion(taskId: Long) {
        val task = getTaskById(taskId)
        task?.let {
            taskDao.updateTaskCompletion(taskId, !it.isCompleted)
        }
    }
}
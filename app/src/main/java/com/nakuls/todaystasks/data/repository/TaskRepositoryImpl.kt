package com.nakuls.todaystasks.data.repository

import com.nakuls.todaystasks.data.local.dao.TaskDao
import com.nakuls.todaystasks.data.local.entity.TaskEntity
import com.nakuls.todaystasks.data.local.entity.toTask
import com.nakuls.todaystasks.data.local.entity.toTaskEntity
import com.nakuls.todaystasks.domain.model.Task
import com.nakuls.todaystasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun getTasks(): Flow<List<Task>> {
        return taskDao.getAll().map { taskEntityList ->
            taskEntityList.map { it.toTask() }
        }
    }

    override suspend fun addTask(task: Task) {
        taskDao.insert(task.toTaskEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.update(task.toTaskEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.delete(task.toTaskEntity())
    }
}







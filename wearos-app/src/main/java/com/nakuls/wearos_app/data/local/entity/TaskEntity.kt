package com.nakuls.wearos_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nakuls.wearos_app.domain.model.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val description: String? = "",
    val isCompleted: Boolean = false,
    val createdAt: Long? = System.currentTimeMillis(),
    val updatedAt: Long? = System.currentTimeMillis()
)

fun TaskEntity.toTask() = Task(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Task.toTaskEntity() = TaskEntity(
    id = id,
    title = title,
    description = description ?: "",
    isCompleted = isCompleted,
    createdAt = createdAt,
    updatedAt = updatedAt
)

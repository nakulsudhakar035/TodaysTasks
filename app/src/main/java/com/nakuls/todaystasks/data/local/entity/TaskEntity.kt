package com.nakuls.todaystasks.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nakuls.todaystasks.domain.model.Task
import com.nakuls.todaystasks.model.Location

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val locationLatitude: Double? = null,
    val locationLongitude: Double? = null,
    val locationName: String? = null
)

fun TaskEntity.toTask() = Task(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    createdAt = createdAt,
    updatedAt = updatedAt,
    location = if (locationLatitude != null && locationLongitude != null) {
        Location(
            latitude = locationLatitude,
            longitude = locationLongitude,
            name = locationName ?: "Random"
        )
    } else {
        null
    }
)

fun Task.toTaskEntity() = TaskEntity(
    id = if (id == 0L) null else id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    createdAt = createdAt,
    updatedAt = updatedAt,
    locationLatitude = location?.latitude,
    locationLongitude = location?.longitude,
    locationName = location?.name
)
package com.nakuls.todaystasks.domain.model

import com.nakuls.todaystasks.model.Location

data class Task(
    val id: Long? = null,
    val title: String,
    val description: String = "",
    val location: Location? = null,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)


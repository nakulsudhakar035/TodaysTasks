package com.nakuls.wearos_app.domain.model

data class Task(
    val id: Long? = null,
    val title: String,
    val description: String? = "",
    val isCompleted: Boolean = false,
    val createdAt: Long? = System.currentTimeMillis(),
    val updatedAt: Long? = System.currentTimeMillis()
) {
    companion object {
        fun createNew(
            title: String,
            description: String = "",
            isCompleted: Boolean = false
        ): Task {
            val now = System.currentTimeMillis()
            return Task(
                id = null,
                title = title,
                description = description,
                isCompleted = isCompleted,
                createdAt = now,
                updatedAt = now
            )
        }
    }
}

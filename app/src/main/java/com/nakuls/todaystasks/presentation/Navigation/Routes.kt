package com.nakuls.todaystasks.presentation.Navigation

object Routes {
    const val TASK_LIST = "taskList"
    const val ADD_TASK = "addTask"
    const val EDIT_TASK = "editTask/{taskId}"

    // Helper function for navigation with parameters
    fun editTask(taskId: Long) = "editTask/$taskId"
}
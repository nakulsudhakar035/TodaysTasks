package com.nakuls.todaystasks.data.sync

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object WearSyncHelper {
    // Paths for data exchange
    const val TASK_SYNC_PATH = "/task_sync"
    const val TASK_UPDATE_PATH = "/task_update"
    const val TASK_DELETE_PATH = "/task_delete"

    // Key constants for data map
    const val KEY_TASKS = "tasks"
    const val KEY_TASK_ID = "task_id"
    const val KEY_TASK_TITLE = "task_title"
    const val KEY_TASK_COMPLETED = "task_completed"
    const val KEY_OPERATION = "operation"
    const val KEY_TIMESTAMP = "timestamp"

    // Operation types
    const val OP_SYNC_ALL = "sync_all"
    const val OP_UPDATE_TASK = "update_task"
    const val OP_DELETE_TASK = "delete_task"
    const val OP_CREATE_TASK = "create_task"

    // Error messages
    const val ERROR_NO_CONNECTION = "no_connection"
    const val ERROR_SYNC_FAILED = "sync_failed"

    // Get formatted timestamp for sync operations
    fun getCurrentTimestamp(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    }
}
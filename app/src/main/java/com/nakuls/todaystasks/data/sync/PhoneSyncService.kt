package com.nakuls.todaystasks.data.sync

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.NodeClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.PutDataRequest
import com.google.android.gms.wearable.Wearable
import com.google.gson.Gson
import com.nakuls.todaystasks.domain.model.Task
import kotlinx.coroutines.tasks.await

class PhoneSyncService(private val context: Context) {
    private val dataClient: DataClient = Wearable.getDataClient(context)
    private val nodeClient: NodeClient = Wearable.getNodeClient(context)

    suspend fun syncAllTasksToWatch(tasks: List<Task>) {
        Log.d("Weather-PhoneSync", "📱 Starting sync with ${tasks.size} tasks")
        try {
            // Get connected nodes
            val nodes = nodeClient.connectedNodes.await()
            Log.d("Weather-PhoneSync", "📱 Connected nodes: ${nodes.size}")

            if (nodes.isEmpty()) {
                Log.d("Weather-SyncService", "No nodes connected")
                return
            }

            nodes.forEach { node ->
                Log.d("PhoneSync", "📱 Node: ${node.displayName}, ID: ${node.id}, Nearby: ${node.isNearby}")
            }

            // Convert tasks to data map
            val taskData = tasks.map { task ->
                mapOf(
                    WearSyncHelper.KEY_TASK_ID to task.id,
                    WearSyncHelper.KEY_TASK_TITLE to task.title,
                    WearSyncHelper.KEY_TASK_COMPLETED to task.isCompleted
                )
            }

            // Put data item for sync
            val dataMap = PutDataMapRequest.create(WearSyncHelper.TASK_SYNC_PATH).apply {
                dataMap.putString(WearSyncHelper.KEY_TASKS, Gson().toJson(taskData))
                dataMap.putString(WearSyncHelper.KEY_OPERATION, WearSyncHelper.OP_SYNC_ALL)
            }.asPutDataRequest().setUrgent()
            Log.d("Weather-PhoneSync", "📱 Sending data to wearables...")

            // Send to all connected nodes
            val result = dataClient.putDataItem(dataMap).await()
            Log.d("Weather-PhoneSync", "Data sync result: $result")

        } catch (e: Exception) {
            Log.e("Weather-PhoneSync", "Error syncing tasks to watch", e)
        }
    }

    // You'll add more methods later for handling updates from watch
}
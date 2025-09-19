package com.nakuls.wearos_app.data.sync

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataItem
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
import com.google.gson.Gson
import com.nakuls.wearos_app.data.repository.TaskRepository
import com.nakuls.wearos_app.domain.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class WatchSyncService(
    private val context: Context,
    private val taskRepository: TaskRepository
) {
    private val dataClient: DataClient = Wearable.getDataClient(context)

    fun startListeningForUpdates() {
        Log.d("Weather-WatchSync", "Attempting to start data layer listener...")
        dataClient.addListener { dataEvents ->
            for (event in dataEvents) {
                Log.d("Weather-WatchSync", "Event type: ${event.type}, URI: ${event.dataItem.uri}")
                if (event.type == DataEvent.TYPE_CHANGED) {
                    when (event.dataItem.uri.path) {
                        WearSyncHelper.TASK_SYNC_PATH -> {
                            Log.d("Weather-WatchSync", "Task sync event received!")
                            handleIncomingTasks(event.dataItem)
                        }
                        else -> {
                            Log.d("Weather-WatchSync", "Unknown path: ${event.dataItem.uri.path}")
                        }
                        // You'll add more cases later for task updates
                    }
                }
            }
        }.addOnSuccessListener {
            Log.d("Weather-WatchSync", "✅ Data layer listener registered successfully")
        }.addOnFailureListener { e->
            Log.e("Weather-WatchSync", "❌ Failed to register listener: ${e.message}")
        }
    }

    private fun handleIncomingTasks(dataItem: DataItem) {
        Log.d("Weather-WatchSync", "Handling incoming tasks...")
        try {
            DataMapItem.fromDataItem(dataItem).dataMap.let { dataMap ->
                val operation = dataMap.getString(WearSyncHelper.KEY_OPERATION)
                val tasksJson = dataMap.getString(WearSyncHelper.KEY_TASKS)

                when (operation) {
                    WearSyncHelper.OP_SYNC_ALL -> {
                        val jsonArray = JSONArray(tasksJson)
                        val tasks = mutableListOf<Task>()
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            tasks.add(
                                Task(
                                    id = jsonObject.getLong(WearSyncHelper.KEY_TASK_ID),
                                    title = jsonObject.getString(WearSyncHelper.KEY_TASK_TITLE),
                                    isCompleted = jsonObject.getBoolean(WearSyncHelper.KEY_TASK_COMPLETED)
                                )
                            )
                        }

                        // Update your local repository
                        CoroutineScope(Dispatchers.IO).launch {
                            taskRepository.saveAllTasks(tasks)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Weather-WatchSync", "Error processing incoming tasks", e)
        }
    }

    // You'll add more methods later for sending updates to phone
}
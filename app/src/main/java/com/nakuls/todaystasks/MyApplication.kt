package com.nakuls.todaystasks

import android.app.Application
import android.util.Log
import com.nakuls.todaystasks.data.sync.PhoneSyncService
import com.nakuls.todaystasks.di.appModule
import com.nakuls.todaystasks.domain.repository.TaskRepository
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("Weather-KoinDebug", "Starting Koin initialization")
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    appModule
                )
            )
        }
        Log.d("Weather-KoinDebug", "Koin initialization complete")
        // Test if dependencies can be retrieved
        try {
            val koin = getKoin()
            val repo = koin.get<TaskRepository>()
            val syncService = koin.get<PhoneSyncService>()
            Log.d("Weather-KoinDebug", "Dependencies retrieved successfully")
            Log.d("Weather-KoinDebug", "Repo: ${repo.hashCode()}, SyncService: ${syncService.hashCode()}")
        } catch (e: Exception) {
            Log.e("Weather-KoinDebug", "Failed to get dependencies: ${e.message}", e)
        }
    }
}
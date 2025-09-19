package com.nakuls.wearos_app

import android.app.Application
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.nakuls.wearos_app.data.repository.TaskRepository
import com.nakuls.wearos_app.data.sync.WatchSyncService
import com.nakuls.wearos_app.di.wearAppModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class WearApplication : Application() {

    private val watchSyncService: WatchSyncService by inject()

    override fun onCreate() {
        super.onCreate()

        Log.d("Weather-WatchApp", "Application onCreate started")

        // Check if Wearable API is available
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)

        if (resultCode == ConnectionResult.SUCCESS) {
            Log.d("Weather-WatchApp", "✅ Google Play Services available")
        } else {
            Log.e("Weather-WatchApp", "❌ Google Play Services not available: $resultCode")
            apiAvailability.getErrorString(resultCode)?.let {
                Log.e("Weather-WatchApp", "Error: $it")
            }
        }

        startKoin {
            androidContext(this@WearApplication)
            modules(wearAppModule)
        }

//        try {
//            watchSyncService.startListeningForUpdates()
//            Log.d("Weather-WatchApp", "✅ Sync service started successfully")
//        } catch (e: Exception) {
//            Log.e("Weather-WatchApp", "❌ Failed to start sync service: ${e.message}")
//        }
    }
}
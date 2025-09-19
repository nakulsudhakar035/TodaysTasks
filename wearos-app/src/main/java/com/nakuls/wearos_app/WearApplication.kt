package com.nakuls.wearos_app

import android.app.Application
import com.nakuls.wearos_app.di.wearAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class WearApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WearApplication)
            modules(wearAppModule)
        }
    }
}
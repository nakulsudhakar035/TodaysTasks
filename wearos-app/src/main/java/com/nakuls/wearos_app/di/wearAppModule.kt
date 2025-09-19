package com.nakuls.wearos_app.di

import com.nakuls.wearos_app.data.local.database.WearDatabase
import com.nakuls.wearos_app.data.repository.TaskRepository
import com.nakuls.wearos_app.data.repository.TaskRepositoryImpl
import com.nakuls.wearos_app.data.sync.WatchSyncService
import com.nakuls.wearos_app.domain.usecase.GetTasksUseCase
import com.nakuls.wearos_app.domain.usecase.InsertTaskUseCase
import com.nakuls.wearos_app.domain.usecase.ToggleTaskUseCase
import com.nakuls.wearos_app.presentation.WearTasksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val wearAppModule = module {
    // Database
    single { WearDatabase.getDatabase(androidContext()) }
    single { get<WearDatabase>().taskDao() }
    single<TaskRepository> {
        TaskRepositoryImpl(get())
    }
    single { WatchSyncService(androidContext(), get()) }

    // Repository
    single { TaskRepositoryImpl(get()) }

    // Use Cases
    factory { GetTasksUseCase(get()) }
    factory { ToggleTaskUseCase(get()) }
    factory { InsertTaskUseCase(get()) }

    viewModel { WearTasksViewModel(get(), get(), get(), get()) }

}
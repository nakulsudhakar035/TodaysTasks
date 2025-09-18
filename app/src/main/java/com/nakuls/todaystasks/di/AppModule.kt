package com.nakuls.todaystasks.di

import android.content.Context
import androidx.room.Room
import com.nakuls.todaystasks.data.local.database.AppDatabase
import com.nakuls.todaystasks.data.repository.TaskRepositoryImpl
import com.nakuls.todaystasks.domain.repository.TaskRepository
import com.nakuls.todaystasks.domain.usecase.AddTaskUseCase
import com.nakuls.todaystasks.domain.usecase.DeleteTaskUseCase
import com.nakuls.todaystasks.domain.usecase.GetTasksUseCase
import com.nakuls.todaystasks.domain.usecase.UpdateTaskUseCase
import com.nakuls.todaystasks.presentation.task.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Database
    single { createDatabase(androidContext()) }
    single { get<AppDatabase>().taskDao() }

    // Repository
    single<TaskRepository> { TaskRepositoryImpl(get()) }

    // Use Cases
    factory { GetTasksUseCase(get()) }
    factory { AddTaskUseCase(get()) }
    factory { UpdateTaskUseCase(get()) }
    factory { DeleteTaskUseCase(get()) }

    // ViewModels
    viewModel { TaskViewModel(
        get(), get(), get(), get()
    ) }
}

private fun createDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "todo-database"
    ).build()
}
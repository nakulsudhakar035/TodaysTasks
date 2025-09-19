package com.nakuls.wearos_app.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.nakuls.wearos_app.data.local.dao.TaskDao
import com.nakuls.wearos_app.data.local.entity.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WearDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    companion object {
        @Volatile
        private var Instance: WearDatabase? = null

        fun getDatabase(context: Context): WearDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    WearDatabase::class.java,
                    "wear_tasks_db"
                ).build().also { Instance = it }
            }
        }
    }

}
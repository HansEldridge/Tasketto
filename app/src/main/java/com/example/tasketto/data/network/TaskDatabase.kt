package com.example.tasketto.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.tasketto.domain.model.Task
import com.example.tasketto.data.dao.TaskDao
import java.util.Date

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TaskTypeConverters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
}

class TaskTypeConverters {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }

}

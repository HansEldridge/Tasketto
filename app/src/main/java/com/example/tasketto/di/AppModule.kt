package com.example.tasketto.di

import android.content.Context
import androidx.room.Room
import com.example.tasketto.data.dao.TaskDao
import com.example.tasketto.data.network.TaskDatabase
import com.example.tasketto.data.repository.TaskRepository
import com.example.tasketto.domain.repository.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideBookDb(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        TaskDatabase::class.java,
        "task_table"
    ).build()

    @Provides
    fun provideTaskDao( taskDatabase: TaskDatabase) = taskDatabase.taskDao

    @Provides
    fun provideTaskRepository( taskDao: TaskDao): TaskRepository = TaskRepositoryImpl ( taskDao = taskDao )
}
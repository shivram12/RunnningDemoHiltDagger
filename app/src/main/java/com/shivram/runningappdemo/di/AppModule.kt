package com.shivram.runningappdemo.di

import android.content.Context
import androidx.room.Room
import com.shivram.runningappdemo.local.database.RunningDatabase
import com.shivram.runningappdemo.utils.Constans.RUNNING_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(@ApplicationContext context:Context) = Room.databaseBuilder(
        context,RunningDatabase::class.java,RUNNING_DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideRunningDao(runningDatabase: RunningDatabase) = runningDatabase.getRunDao()
}
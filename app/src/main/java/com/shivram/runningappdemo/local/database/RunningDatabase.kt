package com.shivram.runningappdemo.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shivram.runningappdemo.local.converters.Converters
import com.shivram.runningappdemo.local.dao.RunDao
import com.shivram.runningappdemo.local.model.Run

@Database(entities = [Run::class],version = 1)
@TypeConverters(Converters::class)
abstract class RunningDatabase :RoomDatabase(){

    abstract fun getRunDao(): RunDao


}
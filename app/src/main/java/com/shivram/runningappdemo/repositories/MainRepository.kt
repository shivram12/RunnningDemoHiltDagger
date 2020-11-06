package com.shivram.runningappdemo.repositories

import com.shivram.runningappdemo.local.dao.RunDao
import com.shivram.runningappdemo.local.model.Run
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDao :RunDao
) {
    suspend fun insertRunRepository(run:Run) = runDao.insertRun(run)
    suspend fun deleteRunRepository(run:Run) = runDao.deleteRun(run)

    fun getAllRunsSortedByDate() = runDao.getAllRunsSortedByDate()

    fun getAllRunsSortedByDistance() = runDao.getAllRunsSortedByDistance()

    fun getAllRunsSortedByTimeInMillis() = runDao.getAllRunsSortedByTimeInMillis()

    fun getAllRunsSortedByAvgSpeed() = runDao.getAllRunsSortedByAvgSpeed()

    fun getAllRunsSortedByCaloriesBurned() = runDao.getAllRunsSortedByCaloriesBurned()

    fun getTotalAvgSpeed() = runDao.getTotalAvgSpeed()

    fun getTotalDistance() = runDao.getTotalDistance()

    fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()

    fun getTotalTimeInMillis() = runDao.getTotalTimeInMillis()
}
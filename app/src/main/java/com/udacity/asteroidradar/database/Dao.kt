package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import java.util.ArrayList

@Dao
interface dataAccess {

    @Query("select * from asteroid")
    fun getAsteroidList(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid WHERE closeApproachDate >= :week ORDER by closeApproachDate ASC")
    fun getWeekAsteroids(week: String) : LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid WHERE closeApproachDate == :day ORDER by closeApproachDate ASC")
    fun getTodayAsteroids(day: String) : LiveData<List<Asteroid>>

    @Query("delete from asteroid")
    fun Delete()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroidList: ArrayList<Asteroid>)
}
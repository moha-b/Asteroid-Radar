package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.DEFAULT_END_DATE_DAYS
import com.udacity.asteroidradar.api.ABI
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.myDatabase
import com.udacity.asteroidradar.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class Repository(val database: myDatabase) {

    val asteroid :LiveData<List<Asteroid>>  = database.addToDB.getAsteroidList()
    val weekAsteroid :LiveData<List<Asteroid>>  = database.addToDB.getWeekAsteroids(DEFAULT_END_DATE_DAYS.toString())
    private val images = MutableLiveData<Image>()
    val image:LiveData<Image> = images

    fun getTodayAsteroid(startDate: String):LiveData<List<Asteroid>> {
        return database.addToDB.getTodayAsteroids(startDate)
    }
    fun getWeekAsteroid(week: String):LiveData<List<Asteroid>> {
        return database.addToDB.getWeekAsteroids(week)
    }
    suspend fun refreshDates(start:String, end:String, api:String){

        withContext(Dispatchers.IO){
            val data = parseAsteroidsJsonResult(
                JSONObject(
                    ABI.server.getProperties(
                        start, end, api
                    )
                )
            )
            database.addToDB.Delete()
            database.addToDB.insertAll(data)
        }
    }
    suspend fun callImages(api: String){

        val scope = withContext(Dispatchers.IO){
            return@withContext ABI.server.getImages(api)
        }
        scope.let {
            images.value = it
        }
    }
    fun cashImages(name: String,sort: String){
        images.value = Image(name,sort)
    }
}
package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Constants.Api
import com.udacity.asteroidradar.Constants.Worker_String
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.database.myDatabase
import com.udacity.asteroidradar.main.Repository
import java.text.SimpleDateFormat
import java.util.*

class Work(context :Context, Parameter :WorkerParameters):CoroutineWorker(context, Parameter) {
companion object{
    val worker_String = Worker_String
}

    lateinit var database: myDatabase
    lateinit var repo: Repository
    override suspend fun doWork(): Result {

        database = getDatabase(applicationContext)
        repo = Repository(database)

        val day = Calendar.getInstance()

        val date = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val thisDay = date.format(day.time)

        day.add(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS)

        val last = day.time
        val lastDay = date.format(last)


        return try {
            repo.refreshDates(
                thisDay, lastDay,Api
            )
            Result.success()
        }
        catch (error :Exception){
            Result.retry()
        }
    }
}
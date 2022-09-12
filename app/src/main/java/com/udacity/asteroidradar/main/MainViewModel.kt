package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.provider.SyncStateContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.Api
import com.udacity.asteroidradar.Constants.DEFAULT_END_DATE_DAYS
import com.udacity.asteroidradar.Image
import com.udacity.asteroidradar.database.getDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log
import kotlin.reflect.jvm.internal.impl.load.java.Constant

private const val URL: String ="IMAGE_URL"
private const val picSort: String ="IMAGE_SORT"

class MainViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var start:String
    lateinit var end:String
    private val database = getDatabase(application)
    private val repo = Repository(database)


    var share: SharedPreferences = application.getSharedPreferences("shareMe", MODE_PRIVATE)

    init {
        Refresh()
    }

    fun Refresh() {
        update()
        viewModelScope.launch {
            try{
                repo.refreshDates(start,end, Api)
                Log.i("gg",start)
                Log.i("gg_end",end)
                Log.i("gg_api", Api)

            }
            catch (error:Exception){
                Log.i("viewModel dates", "Error $error")
            }
            try{
                repo.callImages(Api)
            }
            catch (error:Exception){
                share.getString(URL,"")?.let {
                    repo.cashImages(it, share.getString(picSort,"")!!)
                }
                Log.i("viewModel image", "Error $error")
            }
        }
    }

    val asteroids = repo.asteroid
    val dailyAsteroid = repo.getTodayAsteroid(start)
    val weeklyAsteroid = repo.getWeekAsteroid(start)
    val pic = repo.image
    fun update(){
        val day = Calendar.getInstance()

        val date = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT,Locale.getDefault())
        start = date.format(day.time)

        day.add(Calendar.DAY_OF_YEAR,DEFAULT_END_DATE_DAYS)

        val last = day.time
        end = date.format(last)
    }
    fun getCashed(image: Image){
        share.edit().putString(URL,image.url)
        share.edit().putString(picSort,image.title)
        share.edit().apply()
    }

}

//class ViewModelFactory(private val dataSource: DAO, private val application: Application): ViewModelProvider.Factory{
//    /**
//     * Creates a new instance of the given `Class`.
//     * @param modelClass a `Class` whose instance is requested
//     * @return a newly created ViewModel
//     */
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
//            return MainViewModel(dataSource, application) as T
//        }
//        throw IllegalArgumentException("view Model Unknown")
//    }
//}
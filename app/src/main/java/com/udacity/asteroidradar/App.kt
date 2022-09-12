package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class App: Application() {
    val app = CoroutineScope(Dispatchers.Default)

    fun init(){
        app.launch {
            setWorker()
        }
    }

    fun setWorker() {
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true).setRequiresBatteryNotLow(true).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeat = PeriodicWorkRequestBuilder<Worker>(2, TimeUnit.DAYS)
            .setConstraints(constraint).build()


        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                Work.worker_String, ExistingPeriodicWorkPolicy.KEEP,
                repeat
            )
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }
}
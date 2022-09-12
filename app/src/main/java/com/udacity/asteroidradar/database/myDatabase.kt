package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid

@Database(entities = [Asteroid::class], version = 1)
abstract class myDatabase : RoomDatabase() {

    abstract val addToDB :dataAccess
}

    private lateinit var INSTANCE :myDatabase

    fun getDatabase(context: Context): myDatabase{
        synchronized(myDatabase::class.java){

            if (!::INSTANCE.isInitialized){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    myDatabase::class.java,
                    "AsteroidDatabase"
                ).build()
            }
            return INSTANCE
        }
    }
package com.udacity.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/*
* API
* https://api.nasa.gov/planetary/apod?api_key=LvUBFPUzTy77kWWxqwwnuIqEyLc6YTFFPKC0f81n
* LvUBFPUzTy77kWWxqwwnuIqEyLc6YTFFPKC0f81n
*/

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

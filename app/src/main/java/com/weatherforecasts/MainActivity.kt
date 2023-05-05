package com.weatherforecasts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.weatherforecasts.openweathermap.OpenWeather

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openWeather = OpenWeather()
        openWeather.getLocations("Petersburg")
    }
}
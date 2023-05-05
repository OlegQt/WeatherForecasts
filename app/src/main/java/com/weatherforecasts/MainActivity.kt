package com.weatherforecasts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.weatherforecasts.openweathermap.CitiesLocation
import com.weatherforecasts.openweathermap.OpenWeather
import com.weatherforecasts.openweathermap.UpdateWeatherInfo

class MainActivity : AppCompatActivity() {
    var txtInfo:TextView? = null

    private val updateWeather = object :UpdateWeatherInfo{
        override fun updateCityLocation(cityLocation: List<CitiesLocation>) {
            if(txtInfo!=null){
                val strBuilder:StringBuilder = java.lang.StringBuilder()
                cityLocation.forEach{
                    strBuilder.append(it.name).append("\n")
                        .append("${it.lat}  ${it.lon} \n")
                        .append("country - ${it.country}")
                }
                txtInfo?.text = strBuilder.toString()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtInfo = findViewById(R.id.txt_info)

        val openWeather = OpenWeather(updateWeather)
        openWeather.getLocations("Petersburg")
    }
}
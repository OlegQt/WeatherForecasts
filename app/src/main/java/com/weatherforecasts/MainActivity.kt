package com.weatherforecasts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.weatherforecasts.openweathermap.CitiesLocation
import com.weatherforecasts.openweathermap.OpenWeather
import com.weatherforecasts.openweathermap.UpdateWeatherInfo

class MainActivity : AppCompatActivity() {
    var txtInfo: TextView? = null
    var btnAction: Button? = null
    var txtCity: EditText? = null

    val openWeather = OpenWeather(null)

    private val updateWeather = object : UpdateWeatherInfo {
        override fun updateCityLocation(cityLocation: List<CitiesLocation>) {
            if (txtInfo != null) {
                val strBuilder: StringBuilder = java.lang.StringBuilder()
                cityLocation.forEach {
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
        deployUi()
        uiBehaviour()

        openWeather.setNewListener(updateWeather)
        //openWeather.getLocations("Petersburg")
    }

    fun deployUi() {
        txtInfo = findViewById(R.id.txt_info)
        txtCity = findViewById(R.id.txt_city)
        btnAction = findViewById(R.id.btn_action)
    }
    fun uiBehaviour(){
        btnAction?.setOnClickListener {
            if(!txtCity?.text.isNullOrEmpty()){
                openWeather.getLocations(txtCity?.text.toString())
            }
        }
    }
}
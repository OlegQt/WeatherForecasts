package com.weatherforecasts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.weatherforecasts.openweathermap.Cities
import com.weatherforecasts.openweathermap.OpenWeather
import com.weatherforecasts.openweathermap.UpdateWeatherInfo

class MainActivity : AppCompatActivity() {
    private var txtInfo: TextView? = null
    private var btnAction: Button? = null
    private var txtCity: EditText? = null

    private val openWeather = OpenWeather(null)
    private val cityLocations = mutableListOf<Cities>()

    private val updateWeather = object : UpdateWeatherInfo {
        override fun updateCityLocation(cityLocationList: List<Cities>) {
            updateCityList(cityLocationList)
        }

        override fun message(msg: String) {
            Snackbar.make(txtInfo!!, msg, Snackbar.LENGTH_SHORT).show()
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

    private fun deployUi() {
        txtInfo = findViewById(R.id.txt_info)
        txtCity = findViewById(R.id.txt_city)
        btnAction = findViewById(R.id.btn_action)
    }

    private fun uiBehaviour() {
        btnAction?.setOnClickListener {
            if (!txtCity?.text.isNullOrEmpty()) {
                openWeather.getLocations(txtCity?.text.toString())
            }
        }

        txtInfo?.setOnClickListener {
            val location = cityLocations[0]
            openWeather.getWeather(location.lat, location.lon)
        }
    }

    private fun updateCityList(cities: List<Cities>) {
        cities.forEach {
            cityLocations.add(it)
        }
        if(txtInfo!=null){
            val strBuild = StringBuilder()
            this.cityLocations.forEach {
                strBuild.append(it.toString()).append("\n")
            }
            txtInfo?.text = strBuild.toString()
        }
    }
}
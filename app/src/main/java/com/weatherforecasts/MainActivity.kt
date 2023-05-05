package com.weatherforecasts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.weatherforecasts.openweathermap.*

class MainActivity : AppCompatActivity() {
    private var txtInfo: TextView? = null
    private var btnAction: Button? = null
    private var txtCity: EditText? = null

    private val openWeather = OpenWeather(null) // Экземпляр предсказателя погоды

    private val cities = mutableSetOf<City>() // Глобальное хранилище городов

    // Слушатель для обновления данных о погоде
    private val updateWeather = object : UpdateWeatherInfo {
        override fun inserCity(cityLocationList: List<Cities>) {
            addCity(cityLocationList)
            printAllCities()
        }

        override fun message(msg: String) {
            Snackbar.make(txtInfo!!, msg, Snackbar.LENGTH_SHORT).show()
            printAllCities()
        }

        override fun UpdateCurrentWeather(weather: CityWeather, cityName: String) {
            updateWeather(weather, cityName)
            printAllCities()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        deployUi() // Развертываем GUI
        uiBehaviour() // Вешаем слушателей

        openWeather.setNewListener(updateWeather) // Инициализируем слушателя внутри WeatherApi
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
            cities.forEach {
                openWeather.getWeather(it.lat, it.lon, it.cityName)
            }

        }
    }

    private fun printAllCities() {
        val strBuild = StringBuilder()
        this.cities.forEach {
            strBuild.append(it.cityName)
                .append("T=${it.temperature}")
                .append("\n")
        }
        txtInfo?.text = strBuild.toString()

    }

    private fun addCity(cities: List<Cities>) {
        cities.forEach {
            this.cities.add(
                City(
                    it.name,
                    0.0,
                    it.lat,
                    it.lon
                )
            )
        }
    }

    private fun updateWeather(weather: CityWeather, cityName: String) {
        this.cities.forEach {
            if (it.cityName.equals(cityName)) {
                it.temperature = weather.main.temp
            }
        }
    }
}
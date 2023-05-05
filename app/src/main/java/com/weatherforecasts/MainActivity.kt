package com.weatherforecasts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.weatherforecasts.cityrecycler.CityAdapter
import com.weatherforecasts.openweathermap.*

class MainActivity : AppCompatActivity() {
    private var txtInfo: TextView? = null
    private var btnAction: Button? = null
    private var btnUpdate: Button? = null
    private var txtCity: EditText? = null
    private var recyclerCity: RecyclerView? = null


    private val openWeather = OpenWeather(null) // Экземпляр предсказателя погоды

    private val cities = mutableSetOf<City>() // Глобальное хранилище городов
    private val cityAdapter = CityAdapter(cities)

    // Слушатель для обновления данных о погоде
    private val weatherListener = object : UpdateWeatherInfo {
        override fun inserCity(cityLocationList: List<Cities>) {
            addCity(cityLocationList)
        }

        override fun message(msg: String) {
            Snackbar.make(txtInfo!!, msg, Snackbar.LENGTH_SHORT).show()
        }

        override fun UpdateCurrentWeather(weather: CityWeather, cityName: String) {
            updateWeather(weather, cityName)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        deployUi() // Развертываем GUI
        uiBehaviour() // Вешаем слушателей

        openWeather.setNewListener(weatherListener) // Инициализируем слушателя внутри WeatherApi
    }

    private fun deployUi() {
        txtInfo = findViewById(R.id.txt_info)
        txtCity = findViewById(R.id.txt_city)
        btnAction = findViewById(R.id.btn_action)
        btnUpdate = findViewById(R.id.btn_update)
        recyclerCity = findViewById(R.id.recycler_city)

        val layOut=LinearLayoutManager(this)
        layOut.orientation = RecyclerView.HORIZONTAL
        recyclerCity?.layoutManager = layOut
        recyclerCity?.adapter = this.cityAdapter
    }

    private fun uiBehaviour() {
        btnAction?.setOnClickListener {
            if (!txtCity?.text.isNullOrEmpty()) {
                openWeather.getLocations(txtCity?.text.toString())
            }
        }

        btnUpdate?.setOnClickListener {
            cities.forEach {
                openWeather.getWeather(it.lat, it.lon, it.cityName)
            }
        }
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
        this.cityAdapter.notifyDataSetChanged()
    }

    private fun updateWeather(weather: CityWeather, cityName: String) {
        var iterator = 0
        this.cities.forEach {
            if (it.cityName.equals(cityName)) {
                it.temperature = weather.main.temp
                this.cityAdapter.notifyItemChanged(iterator)
            }
            iterator++
        }
    }

}
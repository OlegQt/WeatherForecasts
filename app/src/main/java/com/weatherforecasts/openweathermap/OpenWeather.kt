package com.weatherforecasts.openweathermap

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeather(var listener: UpdateWeatherInfo?) {
    private val baseUrl = "https://api.openweathermap.org/"
    private val appKey = "ce2b06f255f2307c07504a706c9d920d"
    private val TAG = "OpenWeather"


    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val openWeather: OpenWeatherApi = retrofit.create(OpenWeatherApi::class.java)

    fun setNewListener(listener: UpdateWeatherInfo) {
        this.listener = listener
    }

    fun getLocations(cityName: String) {
        val call = openWeather.getCitiesLocation(cityName, appKey)
        call.enqueue(object : Callback<List<Cities>> {
            override fun onResponse(
                call: Call<List<Cities>>,
                response: Response<List<Cities>>
            ) {
                //Log.d(TAG,response.code().toString())
                if (response.code() == 200) {
                    val citiesLocations = mutableListOf<Cities>()
                    if (!response.body().isNullOrEmpty()) {
                        response.body()?.forEach {
                            citiesLocations.add(it)
                        }
                        listener?.updateCityLocation(citiesLocations)
                    }
                }
            }

            override fun onFailure(call: Call<List<Cities>>, t: Throwable) {
                Log.d(TAG, "fail")
            }

        })
    }

    fun getWeather(lat: Double, lon: Double) {
        val call = openWeather.getWeather(lat, lon, appKey, "metric")
        call.enqueue(object : Callback<CityWeather> {
            override fun onResponse(call: Call<CityWeather>, response: Response<CityWeather>) {
                Log.d(TAG, "W = ${response.code()}")
                val y = response.body()?.main?.temp
                Log.d(TAG, "feels like = $y")
                listener?.message(y.toString())

            }

            override fun onFailure(call: Call<CityWeather>, t: Throwable) {
                Log.d(TAG, "Weather fail")
            }
        })
    }

}
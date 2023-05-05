package com.weatherforecasts.openweathermap

import android.util.Log
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeather {
    private val baseUrl = "https://api.openweathermap.org/"
    private val appKey = "ce2b06f255f2307c07504a706c9d920d"
    private val TAG = "OpenWeather"

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val openWeather = retrofit.create(OpenWeatherApi::class.java)

    fun getLocations(cityName: String) {
        val call = openWeather.getCitiesLocation(cityName, appKey)
        call.enqueue(object :Callback<List<CitiesLocation>>{
            override fun onResponse(
                call: Call<List<CitiesLocation>>,
                response: Response<List<CitiesLocation>>
            ) {
                Log.d(TAG,response.code().toString())
                if(response.code().equals(200)){
                    val citiesLocations: List<CitiesLocation>? =
                        response.body()
                    if (!citiesLocations.isNullOrEmpty()){
                        citiesLocations.forEach{
                            Log.d(TAG,it.name+it.country)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CitiesLocation>>, t: Throwable) {
                Log.d(TAG,"fail".toString())
            }

        })
    }

}
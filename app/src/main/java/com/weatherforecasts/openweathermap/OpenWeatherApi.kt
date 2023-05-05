package com.weatherforecasts.openweathermap

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("/geo/1.0/direct")
    fun getCitiesLocation(
        @Query("q") cityName:String,
        @Query("appid") appKey:String
    ):Call<List<CitiesLocation>>
}
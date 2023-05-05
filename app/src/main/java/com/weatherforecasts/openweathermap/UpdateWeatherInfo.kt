package com.weatherforecasts.openweathermap

interface UpdateWeatherInfo {
    fun updateCityLocation(cityLocation: List<Cities>)
    fun message(msg:String)
}
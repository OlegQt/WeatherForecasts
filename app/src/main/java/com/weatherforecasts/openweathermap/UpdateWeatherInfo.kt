package com.weatherforecasts.openweathermap

interface UpdateWeatherInfo {
    fun updateCityLocation(cityLocation: List<CitiesLocation>)
}
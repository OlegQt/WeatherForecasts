package com.weatherforecasts.openweathermap

interface UpdateWeatherInfo {
    fun inserCity(cityLocation: List<Cities>)
    fun message(msg:String)
    fun UpdateCurrentWeather(weather: CityWeather,cityName: String)
}
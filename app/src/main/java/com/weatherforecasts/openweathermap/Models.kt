package com.weatherforecasts.openweathermap

class Models {
}

data class CitiesLocation(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String
)
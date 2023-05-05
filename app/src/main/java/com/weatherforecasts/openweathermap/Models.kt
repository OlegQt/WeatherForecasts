package com.weatherforecasts.openweathermap

class Models {
}

data class Cities(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val weather:CityWeather
) {
    override fun toString(): String {
        val str = StringBuilder().append("City: $name ($country)\n")
            .append("($lat,$lon)")
        return str.toString()
    }
}

data class CityWeather(
    val visibility: Int,
    val main: CurrentWeather
) {
    data class CurrentWeather(
        val temp: Double,
    )
}
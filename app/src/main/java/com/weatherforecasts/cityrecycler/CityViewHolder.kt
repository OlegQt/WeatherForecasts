package com.weatherforecasts.cityrecycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.weatherforecasts.R
import com.weatherforecasts.openweathermap.City

class CityViewHolder(item:View):ViewHolder(item) {
    val txtCityName:TextView = item.findViewById(R.id.txt_city_name)
    val txtTemperature:TextView = item.findViewById(R.id.txt_city_current_temperature)
    val txtCoordinations:TextView = item.findViewById(R.id.txt_city_cord)

    fun bind(city:City){
        txtCityName.text=city.cityName
        txtTemperature.text=city.temperature.toString()
        txtCoordinations.text = city.lat.toString()
    }
}
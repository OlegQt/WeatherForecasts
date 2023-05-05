package com.weatherforecasts.cityrecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.weatherforecasts.R
import com.weatherforecasts.openweathermap.City

class CityAdapter(private val data:Set<City>):Adapter<CityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.city_holder,parent,false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(data.elementAt(position))
    }

    override fun getItemCount() = data.size
}
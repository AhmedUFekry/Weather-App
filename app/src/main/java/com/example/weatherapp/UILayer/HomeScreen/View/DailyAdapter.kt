package com.example.weatherapp.UILayer.HomeScreen.View

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.DataLayer.Model.DataModels.DailyItem
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.Formatter
import com.example.weatherapp.databinding.DailyItemBinding

class DailyAdapter(val context: Context): ListAdapter<DailyItem, DailyAdapter.DailyViewHolder>(DailyDiffUtil()) {
    class DailyViewHolder(val dailyItemBinding: DailyItemBinding) : RecyclerView.ViewHolder(dailyItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val dailyItemBinding = DailyItemBinding.inflate(inflater, parent,false)
        return DailyViewHolder(dailyItemBinding)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        var current = getItem(position)


        holder.dailyItemBinding.textDay.text = Formatter.getDay(current.dt)

       val imageUrl = "https://openweathermap.org/img/wn/${current?.weather?.get(0)?.icon}@2x.png"
       Glide
          .with(context)
          .load(imageUrl)
          .centerCrop()
          .placeholder(R.drawable.hum_icon)
          .into(holder.dailyItemBinding.imageWeather)
        holder.dailyItemBinding.textDescription.text = current.weather?.get(0)?.description

        holder.dailyItemBinding.textTemperature.text = "${current.temp?.min} / ${current.temp?.max}°C"

    }
    class DailyDiffUtil : DiffUtil.ItemCallback<DailyItem>(){
        override fun areItemsTheSame(oldItem: DailyItem, newItem: DailyItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DailyItem, newItem: DailyItem): Boolean {
            return oldItem == newItem
        }

    }
}
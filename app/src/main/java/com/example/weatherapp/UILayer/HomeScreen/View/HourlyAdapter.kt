package com.example.weatherapp.UILayer.HomeScreen.View

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.DataLayer.Model.DataModels.HourlyItem
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.Formatter
import com.example.weatherapp.databinding.HourlyItemBinding

class HourlyAdapter(private val context: Context): ListAdapter<HourlyItem, HourlyAdapter.HourlyViewHolder>(HourlyDiffUtil()) {
    class HourlyViewHolder(val hourlyItemBinding: HourlyItemBinding) : RecyclerView.ViewHolder(hourlyItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val hourlyItemBinding = HourlyItemBinding.inflate(inflater, parent,false)
        return HourlyViewHolder(hourlyItemBinding)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        var current = getItem(position)

        if (position == 0){
            holder.hourlyItemBinding.textHour.text = context.getString(R.string.now)
        }else {
            holder.hourlyItemBinding.textHour.text =
                Formatter.getFormattedHour(current.dt?.toLong()) ?: "00:00"
        }
        holder.hourlyItemBinding.textTemperature.text = "${current.temp}°C"

        val imageUrl = "https://openweathermap.org/img/wn/${current?.weather?.get(0)?.icon}@2x.png"
        Glide
            .with(context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.hum_icon)
            .into(holder.hourlyItemBinding.tempImage)
    }
}


class HourlyDiffUtil : DiffUtil.ItemCallback<HourlyItem>(){
    override fun areItemsTheSame(oldItem: HourlyItem, newItem: HourlyItem): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: HourlyItem, newItem: HourlyItem): Boolean {
        return oldItem == newItem
    }

}
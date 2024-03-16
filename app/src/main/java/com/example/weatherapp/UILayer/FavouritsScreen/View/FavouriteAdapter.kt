package com.example.weatherapp.UILayer.FavouritsScreen.View

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
import com.example.weatherapp.databinding.FavItemBinding


class FavouriteAdapter(val context: Context,
                       private val onClick : (FavouriteLocationDto) -> Unit

): ListAdapter<FavouriteLocationDto, FavouriteAdapter.FavViewHolder>(FavDiffUtil()) {
    class FavViewHolder(val favItemBinding: FavItemBinding) : RecyclerView.ViewHolder(favItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val favItemBinding = FavItemBinding.inflate(inflater, parent,false)
        return FavViewHolder(favItemBinding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        var current = getItem(position)
        holder.favItemBinding.locationName.text = current.countryName.take(15)
        holder.favItemBinding.locationLat.text =
            "${String.format("%.2f", current.locationKey.lat)}, ${String.format("%.2f", current.locationKey.long)}"
        holder.favItemBinding.cardLocation.setOnClickListener {
            onClick(current)
        }
    }
}



class FavDiffUtil : DiffUtil.ItemCallback<FavouriteLocationDto>(){
    override fun areItemsTheSame(oldItem: FavouriteLocationDto, newItem: FavouriteLocationDto): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: FavouriteLocationDto, newItem: FavouriteLocationDto): Boolean {
        return oldItem == newItem
    }

}
package com.example.weatherapp.UILayer.FavouritsScreen.View

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.DataLayer.Model.Services.LocalDataSource.WeatherLocalDataSourceImpl
import com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource.RemoteDataSourceImpl
import com.example.weatherapp.DataLayer.Model.Services.Repository.WeatherRepoImpl
import com.example.weatherapp.UILayer.FavouritsScreen.ViewModel.FavouriteViewModel
import com.example.weatherapp.Utilities.ViewModelFactory
import com.example.weatherapp.Utilities.ApiState
import com.example.weatherapp.databinding.FragmentFavouriteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavouriteFragment : Fragment() {
    private lateinit var favouriteBinding: FragmentFavouriteBinding
    private lateinit var favViewModel: FavouriteViewModel
    private lateinit var favViewModelFactory: ViewModelFactory
    private lateinit var favAdater: FavouriteAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        favouriteBinding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return favouriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteBinding.fabAddPlace.setOnClickListener {
            val action = FavouriteFragmentDirections.actionFavouriteToMap()
            action.setFav("fav")
            Navigation.findNavController(view).navigate(action)
        }


        favAdater = FavouriteAdapter(requireContext()) {
            val action = FavouriteFragmentDirections.actionFavouriteToHome()
            action.setObj(it)
            Navigation.findNavController(view).navigate(action)
        }

        layoutManager = LinearLayoutManager(context)

        favouriteBinding.favRc.layoutManager = layoutManager

        favouriteBinding.favRc.adapter = favAdater

        favViewModelFactory = ViewModelFactory(
            WeatherRepoImpl.getInstance(
                RemoteDataSourceImpl.getInstance(),
                WeatherLocalDataSourceImpl.getInstance(requireContext())
            )
        )
        favViewModel =
            ViewModelProvider(this, favViewModelFactory).get(FavouriteViewModel::class.java)

        lifecycleScope.launch(Dispatchers.Main) {
            favViewModel.locationList.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        if (it.data.isEmpty()){
                            favouriteBinding.favRc.visibility = View.GONE
                            favouriteBinding.emptyFav.visibility = View.VISIBLE
                            favouriteBinding.noPlaceTv.visibility = View.VISIBLE
                        }else{
                            favouriteBinding.favRc.visibility = View.VISIBLE
                            favouriteBinding.emptyFav.visibility = View.GONE
                            favouriteBinding.noPlaceTv.visibility = View.GONE
                            favAdater.submitList(it.data)
                        }
                    }

                    is ApiState.Failed -> {
                        favouriteBinding.favRc.visibility = View.GONE
                        favouriteBinding.emptyFav.visibility = View.VISIBLE
                        favouriteBinding.noPlaceTv.visibility = View.VISIBLE
                    }

                    is ApiState.Loading -> {
                        Log.i("TAG", "onViewCreated: loading")
                        favouriteBinding.favRc.visibility = View.GONE
                        favouriteBinding.emptyFav.visibility = View.VISIBLE
                        favouriteBinding.noPlaceTv.visibility = View.VISIBLE
                    }

                    else -> {
                        Log.i("TAG", "onViewCreated: else")
                    }
                }
            }
        }


        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val builder = AlertDialog.Builder(context)
                    val position = viewHolder.adapterPosition
                    val location = favAdater.currentList[position]
                    builder.apply {
                        setTitle("Delete")
                        setMessage("Are you sure you want to delete this location?")
                        setPositiveButton("Delete") { dialog, which ->
                            favViewModel.deleteLocation(location)
                        }
                        setNegativeButton("Cancel") { dialog, which ->
                           favAdater.notifyDataSetChanged()
                        }
                        show()
                    }
                }
            }
        )
        itemTouchHelper.attachToRecyclerView(favouriteBinding.favRc)

    }
}
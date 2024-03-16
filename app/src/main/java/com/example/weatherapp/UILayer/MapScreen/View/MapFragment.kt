package com.example.weatherapp.UILayer.MapScreen.View

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherapp.DataLayer.Model.DataModels.FavouriteLocationDto
import com.example.weatherapp.DataLayer.Model.DataModels.LocationKey
import com.example.weatherapp.DataLayer.Model.Services.LocalDataSource.WeatherLocalDataSourceImpl
import com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource.RemoteDataSourceImpl
import com.example.weatherapp.DataLayer.Model.Services.Repository.WeatherRepoImpl
import com.example.weatherapp.R
import com.example.weatherapp.UILayer.FavouritsScreen.ViewModel.FavouriteViewModel
import com.example.weatherapp.Utilities.ViewModelFactory
import com.example.weatherapp.databinding.FragmentMapBinding
import com.example.weatherforecast.utilities.LocationUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener {
    var lat = 0.0
    var lon = 0.0
    private lateinit var mapFragmentBinding: FragmentMapBinding
    private lateinit var gMap: GoogleMap
    private lateinit var favViewModel: FavouriteViewModel
    private lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapFragmentBinding = FragmentMapBinding.inflate(inflater, container, false)
        mapFragmentBinding.map.onCreate(savedInstanceState)
        mapFragmentBinding.cardLocation.visibility = View.GONE
        mapFragmentBinding.map.getMapAsync(this)
        factory = ViewModelFactory(
            WeatherRepoImpl.getInstance(
                RemoteDataSourceImpl.getInstance(),
                WeatherLocalDataSourceImpl.getInstance(requireContext())))
        favViewModel = ViewModelProvider(this, factory).get(FavouriteViewModel::class.java)
        val arguments = MapFragmentArgs.fromBundle(requireArguments())
        val mapStringArg = arguments.fav
        if (mapStringArg == "map"){
            mapFragmentBinding.saveBtnLocation.text = "show Weather Detials"
        }
        mapFragmentBinding.saveBtnLocation.setOnClickListener {
            if (mapStringArg == "map"){
                val action = MapFragmentDirections.actionMapToHome()
                action.setMap("map")
                action.setObj(FavouriteLocationDto(LocationKey(lat,lon)
                    ,LocationUtils.getAddress(requireContext(),lat,lon),"0"))
                Navigation.findNavController(requireView()).navigate(action)
            }else if (mapStringArg == "fav"){
                favViewModel.insertLocation(FavouriteLocationDto(
                    LocationKey(lat,lon)
                    ,LocationUtils.getAddress(requireContext(),lat,lon),"0"))
                Log.i("TAG", "FAV ARGS")
                Navigation.findNavController(it).navigate(R.id.action_map_to_favourite)
            }
        }

        return mapFragmentBinding.root
    }

    override fun onResume() {
        super.onResume()
        mapFragmentBinding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapFragmentBinding.map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapFragmentBinding.map.onDestroy()
    }

    override fun onMapReady(map: GoogleMap) {
        gMap = map
        gMap.setOnMapClickListener(this)
    }

    override fun onMapClick(latLng: LatLng) {
        mapFragmentBinding.cardLocation.visibility = View.VISIBLE
        gMap.clear()
        gMap.addMarker(MarkerOptions().position(latLng).title("Location"))
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7f))
        lat = latLng.latitude
        lon = latLng.longitude
        val latStr = String.format("%.6f", lat)
        val lonStr = String.format("%.6f", lon)
        mapFragmentBinding.locationName.text = LocationUtils.getAddress(requireContext(),lat,lon)
        mapFragmentBinding.locationLat.text = "Lat:  $latStr , Long: $lonStr"
    }

}
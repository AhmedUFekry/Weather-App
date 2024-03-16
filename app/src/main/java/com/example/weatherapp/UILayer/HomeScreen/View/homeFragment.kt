package com.example.weatherapp.UILayer.HomeScreen.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.health.connect.datatypes.units.Length
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.DataLayer.Model.DataModels.WeatherResponse
import com.example.weatherapp.DataLayer.Model.Services.LocalDataSource.WeatherLocalDataSourceImpl
import com.example.weatherapp.DataLayer.Model.Services.RemoteDataSource.RemoteDataSourceImpl
import com.example.weatherapp.DataLayer.Model.Services.Repository.WeatherRepoImpl
import com.example.weatherapp.R
import com.example.weatherapp.UILayer.HomeScreen.ViewModel.HomeViewModel
import com.example.weatherapp.Utilities.ViewModelFactory
import com.example.weatherapp.Utilities.ApiState
import com.example.weatherapp.Utilities.Converter
import com.example.weatherapp.Utilities.Formatter
import com.example.weatherapp.Utilities.LocationHelper
import com.example.weatherapp.Utilities.SettingsConstants
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherforecast.utilities.LocationUtils
import com.example.weatherforecast.utilities.NetworkConnection
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.FileNotFoundException
import java.io.IOException


class homeFragment : Fragment() {
    lateinit var locationHelper: LocationHelper
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var dailyAdapter: DailyAdapter
    private lateinit var hourlyAdapter: HourlyAdapter
    private lateinit var layoutManagerDaily: LinearLayoutManager
    private lateinit var layoutManagerHourly: LinearLayoutManager
    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        return homeBinding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelFactory = ViewModelFactory(
            WeatherRepoImpl.getInstance(
                RemoteDataSourceImpl.getInstance(),
                WeatherLocalDataSourceImpl.getInstance(requireContext())
            )
        )
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        dailyAdapter = DailyAdapter(requireContext())
        hourlyAdapter = HourlyAdapter(requireContext())

        layoutManagerDaily = LinearLayoutManager(context)
        homeBinding.recyclerDailyWeather.layoutManager = layoutManagerDaily
        layoutManagerHourly = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeBinding.recyclerHourlyWeather.layoutManager = layoutManagerHourly

        homeBinding.recyclerDailyWeather.adapter = dailyAdapter
        homeBinding.recyclerHourlyWeather.adapter = hourlyAdapter
        locationHelper = LocationHelper(requireContext())
        val args = homeFragmentArgs.fromBundle(requireArguments())
        val favLocation = args.obj

        if (favLocation!=null){
            homeViewModel.getFavoriteWeather(favLocation.locationKey.lat,favLocation.locationKey.long)
        }else if (args.map == "map"){
            homeViewModel.getFavoriteWeather(favLocation?.locationKey?.lat ?: 0.0,favLocation?.locationKey?.long ?:0.0)
        }else{
            locationHelper.getLocation(requireActivity())
            locationHelper.getLastKnownLocation {
                val args = homeFragmentArgs.fromBundle(requireArguments())
                val favLocation = args.obj

                if (favLocation!=null){
                    if (args.map == "map") {
                        homeViewModel.getFavoriteWeather(
                            favLocation?.locationKey?.lat ?: 0.0,
                            favLocation?.locationKey?.long ?: 0.0
                        )
                    }
                }else{
                    homeViewModel.getCurrentWeatherfor(it.latitude,it.longitude)
                }
            }
        }
        homeBinding.swipeContainer.setOnRefreshListener {

            if (NetworkConnection.checkConnectionState(requireActivity())) {
                homeBinding.swipeContainer.isRefreshing = false
                lifecycleScope.launch(Dispatchers.Main) {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        homeViewModel.weatherStateFlow.collectLatest {
                            when (it) {
                                is ApiState.Success -> {
                                    Log.i("TAG", "onViewCreated: " + it.data.timezone)
                                    setWeatherDataToViews(it.data)
                                    saveResponseToFile(it.data)
                                    homeBinding.progressBar.visibility = View.GONE
                                    homeBinding.background.visibility = View.VISIBLE
                                    homeBinding.home.visibility = View.GONE
                                }

                                is ApiState.Failed -> {
                                    Log.i("TAG", "onViewCreated: failed" + it.msg.toString())
                                    homeBinding.progressBar.visibility = View.GONE
                                    homeBinding.background.visibility = View.GONE
                                    homeBinding.home.visibility = View.VISIBLE
                                }

                                is ApiState.Loading -> {
                                    Log.i("TAG", "onViewCreated: loading")
                                    homeBinding.background.visibility = View.GONE
                                    homeBinding.progressBar.visibility = View.VISIBLE
                                    homeBinding.home.visibility = View.GONE
                                }
                            }
                        }
                    }
                }
            } else {
                homeBinding.swipeContainer.isRefreshing = false
                loadWeatherResponseFromFile()?.let { setWeatherDataToViews(it) }
                Toast.makeText(requireContext(), "Check Your Network Connection", Toast.LENGTH_LONG)
                    .show()

            }
        }

    }



  //add data to the view
  private fun setWeatherDataToViews(weatherResponse: WeatherResponse) {
      val description =  weatherResponse.current?.weather?.get(0)?.description ?: "UnKnow"
      val temp = weatherResponse.current?.temp?.toInt() ?: 0



        val imageUrl = "https://openweathermap.org/img/wn/${weatherResponse.current?.weather?.get(0)?.icon}@2x.png"
       Glide
           .with(requireContext())
           .load(imageUrl)
           .centerCrop()
           .placeholder(R.drawable.hum_icon)
           .into(homeBinding.tempImageDes)

      homeBinding.countryName.text = LocationUtils.getAddress(requireActivity(), weatherResponse?.lat ?: 0.0,weatherResponse.lon ?: 0.0)
      homeBinding.currentData.text = Formatter.getDate(weatherResponse.current?.dt)
      homeBinding.desTemp.text = description
      homeBinding.humitiyValue.text = (weatherResponse.current?.humidity ?: "0").toString() + " %"
      homeBinding.textView2.text = (Converter.getWindSpeed(weatherResponse.current?.windSpeed) ?: "0").toString() + " "+ SettingsConstants.getWindSpeedUnit()
      homeBinding.pressureValue.text = (weatherResponse.current?.pressure  ?: "0").toString()
      homeBinding.cloudValue.text = (weatherResponse.current?.clouds  ?: "0").toString()
      homeBinding.tempValue.text = Converter.getTemperature(temp).toString()
      homeBinding.tempMeasure.text = SettingsConstants.getTemperatureSymbol().toString()
      hourlyAdapter.submitList(weatherResponse.hourly)
      dailyAdapter.submitList(weatherResponse.daily)

  }

    private fun saveResponseToFile(response: WeatherResponse) {
        val context = requireContext().applicationContext
        val filename = "offlineweather.json"
        context.deleteFile(filename)
        val jsonString = Gson().toJson(response)
        context.openFileOutput(filename, Context.MODE_PRIVATE).use { outputStream ->
            outputStream.write(jsonString.toByteArray())
        }
    }


    private fun loadWeatherResponseFromFile(): WeatherResponse? {
        val filename = "offlineweather.json"
        return try {
            val inputStream = requireActivity().openFileInput(filename)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            Gson().fromJson(jsonString, WeatherResponse::class.java)
        } catch (e: FileNotFoundException) {
            null
        } catch (e: IOException) {
            null
        }
    }

}
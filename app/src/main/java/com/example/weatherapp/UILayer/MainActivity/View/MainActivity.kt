package com.example.weatherapp.UILayer.MainActivity.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.Utilities.LocationHelper
import com.example.weatherapp.Utilities.SettingsConstants
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherforecast.sharedprefernces.SharedPreferencesHelper
import com.example.weatherforecast.utilities.LanguageUtils

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var homeActivityMainBinding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferencesHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(homeActivityMainBinding.root)

        sharedPreferences = SharedPreferencesHelper.getInstance(this)
        sharedPreferences.loadData()

        LanguageUtils.setAppLocale(SettingsConstants.getLangCode(), this)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph, homeActivityMainBinding.drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, homeActivityMainBinding.drawerLayout)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        val destinationId = SettingsConstants.getLocation()
        navController.navigate(destinationId)

        NavigationUI.setupWithNavController(
            homeActivityMainBinding.navigationView,
            navController
        )

        homeActivityMainBinding.navigationView.setupWithNavController(navController)
    }
    override fun onResume() {
        super.onResume()
       val locationHelper = LocationHelper(this)
        locationHelper.getLocation(this)
        val isNewSettingsRestart = sharedPreferences.isNewSettingsRestart()
        if (isNewSettingsRestart == 1) {
            sharedPreferences.loadData()
            val destinationId = SettingsConstants.getLocation()
            navController.navigate(destinationId)
            sharedPreferences.saveAsNewSetting(0)
        }
        else {
            sharedPreferences.saveAsNewSetting(0)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }
}
package com.example.newair.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newair.R
import com.example.newair.data.SensorViewModel
import com.example.newair.data.FileManager
import com.example.newair.fragments.home.HomeFragmentDirections
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(),
    NavigationBarView.OnItemSelectedListener,
    NavController.OnDestinationChangedListener {

    private lateinit var navController: NavController
    private lateinit var navBar: NavigationBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNavController()
        val viewModel: SensorViewModel by viewModels()
        viewModel.addUserLocations(FileManager(this).readUserLocationsFile())
        viewModel.downloadData()
    }

    private fun setNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navBar = findViewById<View>(R.id.nav_view) as NavigationBarView
        navBar.setupWithNavController(navController)
        navBar.setOnItemSelectedListener(this)
        navController.addOnDestinationChangedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val dir = when (item.itemId) {
            R.id.navigation_map -> HomeFragmentDirections.actionNavigationHomeToNavigationMap(true)
            R.id.navigation_graph -> HomeFragmentDirections.actionNavigationHomeToNavigationGraph()
            else -> null
        }
        if (dir != null) navController.navigate(dir)
        return true
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        navBar.visibility = if (destination.id == R.id.navigation_home) View.VISIBLE else View.GONE
    }

    override fun onPause() {
        val viewModel: SensorViewModel by viewModels()
        FileManager(this).saveUserLocations(viewModel.uiState.value!!.userLocations)
        super.onPause()
    }
}
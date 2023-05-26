package com.vladislaviliev.newair

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.vladislaviliev.newair.data.FileManager
import com.google.android.material.navigation.NavigationBarView
import com.vladislaviliev.newair.fragments.home.HomeFragmentDirections

class Act : AppCompatActivity(), NavigationBarView.OnItemSelectedListener, NavController.OnDestinationChangedListener {

    private val vm: Vm by viewModels()
    private lateinit var navController: NavController
    private lateinit var navBar: NavigationBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        navBar = findViewById(R.id.nav_view)
        navBar.setupWithNavController(navController)
        navBar.setOnItemSelectedListener(this)
        navController.addOnDestinationChangedListener(this)
        vm.addUserLocations(FileManager(this).readUserLocationsFile())
        vm.downloadData()
    }

    override fun onPause() {
        FileManager(this).saveUserLocations(vm.userLocations)
        super.onPause()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navController.navigate(
            if (item.itemId == R.id.navigation_map) HomeFragmentDirections.actionNavigationHomeToNavigationMap(true)
            else HomeFragmentDirections.actionNavigationHomeToNavigationGraph()
        )
        return true
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        navBar.visibility = if (destination.id == R.id.navigation_home) View.VISIBLE else View.GONE
    }
}
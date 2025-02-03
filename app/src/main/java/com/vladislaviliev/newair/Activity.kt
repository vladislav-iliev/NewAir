package com.vladislaviliev.newair

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vladislaviliev.newair.navigation.suiteItems
import com.vladislaviliev.newair.ui.theme.NewAirComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewAirComposeTheme {

                val navController = rememberNavController()
                val currentEntry by navController.currentBackStackEntryAsState()

                NavigationSuiteScaffold(
                    navigationSuiteItems = { suiteItems(navController, currentEntry) },
                    content = { ScreenContainer(navController) }
                )

            }
        }
    }
}

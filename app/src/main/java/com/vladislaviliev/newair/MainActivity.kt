package com.vladislaviliev.newair

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.vladislaviliev.newair.navigationBar.NavBar
import com.vladislaviliev.newair.ui.theme.NewAirComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewAirComposeTheme {

                val appState = viewModel<AppState>()
                val topAppBar by appState.appBar.collectAsStateWithLifecycle()

                val navController = rememberNavController()

                Scaffold(
                    topBar = topAppBar,
                    content = { ScreenContainer(it, navController) },
                    snackbarHost = { SnackbarHost(appState.snackBarHostState) },
                    bottomBar = { NavBar(navController) },
                )

            }
        }
    }
}

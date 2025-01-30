package com.vladislaviliev.newair

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppState : ViewModel() {

    private val _topAppBar: MutableStateFlow<@Composable () -> Unit> = MutableStateFlow({})
    val appBar = _topAppBar.asStateFlow()

    val snackBarHostState = SnackbarHostState()


    fun setTopAppBar(topAppBar: @Composable () -> Unit) {
        viewModelScope.launch {
            _topAppBar.emit(topAppBar)
        }
    }

    fun showSnackBar(message: String) {
        viewModelScope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }
}
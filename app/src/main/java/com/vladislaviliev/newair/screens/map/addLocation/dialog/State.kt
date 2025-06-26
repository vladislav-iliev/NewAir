package com.vladislaviliev.newair.screens.map.addLocation.dialog

import androidx.annotation.StringRes

sealed class State {
    class Idle : State()
    class Loading : State()
    class Success(val name: String) : State()
    class Error(@get:StringRes val msg: Int, val name: String) : State()
}
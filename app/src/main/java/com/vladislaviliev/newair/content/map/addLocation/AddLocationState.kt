package com.vladislaviliev.newair.content.map.addLocation

data class AddLocationState(
    val isAdding: Boolean,
    val successMessage: String,
    val errorMessage: String,
)

package com.vladislaviliev.newair.user.location.paging

sealed class Model {
    data class Header(val char: Char) : Model()
    data class Location(val id: Int, val title: String) : Model()
}
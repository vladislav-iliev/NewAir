package com.vladislaviliev.newair.user.location.paging

sealed class Model {
    class Header(val char: Char) : Model()
    class Location(val id: Int, val title: String) : Model()
}
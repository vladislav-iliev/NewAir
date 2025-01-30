package com.vladislaviliev.newair.user.location.paging

sealed class UserLocationPagerModel {
    class Header(val char: Char) : UserLocationPagerModel()
    class Location(val id: Int, val title: String) : UserLocationPagerModel()
}
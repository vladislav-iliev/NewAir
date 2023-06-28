package com.vladislaviliev.newair.home

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.userlocation.UserLocation

internal class Carousel(fragment: Fragment, private val userLocations: List<UserLocation>) {

    private val locations: List<CharSequence> = mutableListOf("City").apply { addAll(userLocations.map { it.name }) }
    private val arrowLeft = fragment.requireView().findViewById<View>(R.id.carouselArrowLeft)
    private val arrowRight = fragment.requireView().findViewById<View>(R.id.carouselArrowRight)
    private var position = 0

    init {
        val carousel = fragment.requireView().findViewById<ViewPager2>(R.id.carousel)
        carousel.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(page: Int) {
                position = page
                checkArrowsVisibility()
                fragment.redrawReadings()
            }
        })
        carousel.adapter = CarouselAdapter(locations)
        arrowLeft.setOnClickListener { carousel.setCurrentItem(carousel.currentItem - 1, true) }
        arrowRight.setOnClickListener { carousel.setCurrentItem(carousel.currentItem + 1, true) }
        checkArrowsVisibility()
    }

    fun getCurrentLatLng() = if (position == 0) null else userLocations[position - 1].latLng

    private fun checkArrowsVisibility() {
        arrowLeft.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
        arrowRight.visibility = if (position == locations.size - 1) View.INVISIBLE else View.VISIBLE
    }
}
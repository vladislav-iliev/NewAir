package com.vladislaviliev.newair.home

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.vladislaviliev.newair.R

internal class Carousel(root: View, private val data: Data) {

    private val carouselView = root.findViewById<ViewPager2>(R.id.carousel)
    private val arrowLeft = root.findViewById<View>(R.id.carouselArrowLeft)
    private val arrowRight = root.findViewById<View>(R.id.carouselArrowRight)

    init {
        carouselView.adapter = CarouselAdapter(data.locations)
        carouselView.isUserInputEnabled = false
        arrowLeft.setOnClickListener { data.decrementPosition() }
        arrowRight.setOnClickListener { data.incrementPosition() }
    }

    fun redrawPosition() {
        checkArrowsVisibility(data.positionInt, data.locations.size)
        carouselView.currentItem = data.positionInt
    }

    private fun checkArrowsVisibility(pos: Int, maxPos: Int) {
        arrowLeft.visibility = if (pos == 0) View.INVISIBLE else View.VISIBLE
        arrowRight.visibility = if (pos == maxPos - 1) View.INVISIBLE else View.VISIBLE
    }
}
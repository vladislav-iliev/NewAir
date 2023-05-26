package com.vladislaviliev.newair.fragments.home

import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.vladislaviliev.newair.R
import `in`.goodiebag.carouselpicker.CarouselPicker

internal class HomeCarousel(fragment: HomeFragment) {

    private val locations = ArrayList(fragment.resources.getStringArray(R.array.initial_locations).toList())
    private val arrowLeft = fragment.requireView().findViewById<View>(R.id.carouselArrowLeft)
    private val arrowRight = fragment.requireView().findViewById<View>(R.id.carouselArrowRight)
    internal var position = 0

    init {
        locations.addAll(fragment.vm.userLocations.map { it.name })

        val carouselItems = locations.map { CarouselPicker.TextItem(it, 10) }
        val adapter = CarouselPicker.CarouselViewAdapter(fragment.requireContext(), carouselItems, 0)
        adapter.textColor = ContextCompat.getColor(fragment.requireContext(), R.color.white)

        val carousel = fragment.requireView().findViewById<CarouselPicker>(R.id.carousel)
        carousel.clearOnPageChangeListeners()
        carousel.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(page: Int) {
                position = page
                fragment.updateScreen()
            }
        })
        carousel.adapter = adapter
        arrowLeft.setOnClickListener { carousel.setCurrentItem(carousel.currentItem - 1, true) }
        arrowRight.setOnClickListener { carousel.setCurrentItem(carousel.currentItem + 1, true) }
    }

    fun checkArrowsVisibility(position: Int) {
        arrowLeft.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
        arrowRight.visibility = if (position == locations.size - 1) View.INVISIBLE else View.VISIBLE
    }
}
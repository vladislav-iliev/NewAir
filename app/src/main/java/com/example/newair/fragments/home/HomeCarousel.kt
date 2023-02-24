package com.example.newair.fragments.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.newair.R
import com.example.newair.data.user_locations.UserLocation
import `in`.goodiebag.carouselpicker.CarouselPicker
import java.util.*
import kotlin.math.roundToInt

internal class HomeCarousel(private val fragment: HomeFragment) {

    private val carousel: CarouselPicker
    private val carouselArrowLeft: ImageButton
    private val carouselArrowRight: ImageButton
    private val locations: MutableList<String> = ArrayList()
    private val carouselItems: MutableList<CarouselPicker.PickerItem> = ArrayList<CarouselPicker.PickerItem>()
    var currentPos = 0

    init {
        locations.addAll(listOf(*fragment.resources.getStringArray(R.array.initial_locations)))
        val homeFragmentView = fragment.requireView()
        carousel = homeFragmentView.findViewById(R.id.carousel)
        carouselArrowLeft = homeFragmentView.findViewById(R.id.carouselArrowLeft)
        carouselArrowRight = homeFragmentView.findViewById(R.id.carouselArrowRight)
        carouselArrowLeft.setOnClickListener { carousel.setCurrentItem(carousel.currentItem - 1, true) }
        carouselArrowRight.setOnClickListener { carousel.setCurrentItem(carousel.currentItem + 1, true) }
    }

    private fun addCarouselListener() {
        carousel.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                currentPos = position
                fragment.updateScreen()
                checkArrowsVisibility(position)
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    fun addLocations(userLocations: List<UserLocation>) {
        userLocations.forEach { locations.add(it.name) }

        carouselItems.clear()
        for (location in locations) carouselItems.add(CarouselPicker.TextItem(location, 10))
        carousel.adapter = CarouselAdapter(fragment.requireContext(), carouselItems, 0)
        carousel.clearOnPageChangeListeners()
        addCarouselListener()
        checkArrowsVisibility(currentPos)
    }

    fun checkArrowsVisibility(currentPosition: Int) {
        if (currentPosition == 0) carouselArrowLeft.visibility = View.INVISIBLE
        else if (carouselArrowLeft.visibility == View.INVISIBLE) carouselArrowLeft.visibility = View.VISIBLE

        if (currentPosition == locations.size - 1) carouselArrowRight.visibility = View.INVISIBLE
        else if (carouselArrowRight.visibility == View.INVISIBLE) carouselArrowRight.visibility = View.VISIBLE
    }

    /**
     * Created by pavan on 25/04/17.
     * Edited by Vladislav Iliev
     * MIT License
     *
     *
     * Copyright (c) 2017 GoodieBag
     *
     *
     * Permission is hereby granted, free of charge, to any person obtaining a copy
     * of this software and associated documentation files (the "Software"), to deal
     * in the Software without restriction, including without limitation the rights
     * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
     * copies of the Software, and to permit persons to whom the Software is
     * furnished to do so, subject to the following conditions:
     *
     *
     * The above copyright notice and this permission notice shall be included in all
     * copies or substantial portions of the Software.
     *
     *
     * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
     * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
     * SOFTWARE.
     */
    private class CarouselAdapter(
        private val context: Context,
        private val items: List<CarouselPicker.PickerItem>,
        drawable: Int
    ) : CarouselPicker.CarouselViewAdapter(context, items, drawable) {

        private val drawable = if (drawable == 0) `in`.goodiebag.carouselpicker.R.layout.page else drawable

        override fun getCount() = items.size

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(context).inflate(drawable, null)
            val tv = view.findViewById<TextView>(`in`.goodiebag.carouselpicker.R.id.tv)
            tv.setTextColor(ContextCompat.getColor(context, R.color.homeTextColor))
            val pickerItem: CarouselPicker.PickerItem = items[position]
            tv.visibility = View.VISIBLE
            tv.text = pickerItem.text
            val textSize: Int = (pickerItem as CarouselPicker.TextItem).textSize
            if (textSize != 0) tv.textSize = dpToPx(pickerItem.textSize).toFloat()
            view.tag = position
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(obj as View)
        }

        override fun isViewFromObject(view: View, obj: Any) = view === obj

        private fun dpToPx(dp: Int) =
            (dp.toFloat() * (context.resources.displayMetrics.xdpi / 160.toFloat())).roundToInt()
    }
}
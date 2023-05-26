package com.vladislaviliev.newair.fragments.map

import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.vladislaviliev.newair.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

internal class AddLocationMode(private val fragment: Fragment, googleMap: GoogleMap) : OnClickListener {
    private val markerOptions = MarkerOptions().apply {
        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        draggable(false)
        visible(true)
    }
    private var marker: Marker? = null
    private val addBtn = fragment.requireView().findViewById<View>(R.id.addLocationBtn)

    init {
        fragment.requireView().findViewById<View>(R.id.addLocationText).visibility = View.VISIBLE
        googleMap.setOnMapClickListener { touchCoordinates: LatLng ->
            marker?.remove()
            markerOptions.position(touchCoordinates)
            marker = googleMap.addMarker(markerOptions)
            addBtn.visibility = View.VISIBLE
        }
        addBtn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (!isMarkerInNewcastle()) {
            Toast.makeText(fragment.requireContext(), fragment.getString(R.string.location_outside_newcastle), Toast.LENGTH_LONG).show()
            return
        }
        val lat = marker!!.position.latitude.toFloat()
        val lon = marker!!.position.longitude.toFloat()
        NavHostFragment.findNavController(fragment).navigate(MapFragmentDirections.actionNavigationMapToNewLocDialog(lat, lon))
    }

    private fun isMarkerInNewcastle() =
        marker!!.position.latitude in 54.934521..55.055446 && marker!!.position.longitude in -1.698933..-1.483927
}
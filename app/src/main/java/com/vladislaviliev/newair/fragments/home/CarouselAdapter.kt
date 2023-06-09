package com.vladislaviliev.newair.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vladislaviliev.newair.R

internal class CarouselAdapter(private val items: List<String>) : RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.carousel_viewholder, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as TextView).text = items[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
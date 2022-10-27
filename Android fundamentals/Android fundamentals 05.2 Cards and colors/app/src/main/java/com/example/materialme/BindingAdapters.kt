package com.example.materialme

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Sport>?) {
    val adapter = recyclerView.adapter as SportsAdapter
    adapter.submitList(data)
}

@BindingAdapter("loadImage")
fun bindLoadImage(imageView: ImageView, url: Int) {
    Glide.with(imageView).load(url).into(imageView)
}

class BindingAdapters {
}
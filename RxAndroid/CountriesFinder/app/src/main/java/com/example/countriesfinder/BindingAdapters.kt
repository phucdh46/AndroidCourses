package com.example.countriesfinder

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.countriesfinder.data.Country


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Country>?) {
    val adapter = recyclerView.adapter as CountryAdapter
    adapter.submitList(data)
}

class BindingAdapters {
}
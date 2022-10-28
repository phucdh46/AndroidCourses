package com.example.materialme

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.materialme.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private val viewModel: SportViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.selectedSport.observe(this) {

            binding.img.setImageResource(it.imageResource)
            binding.title.text = it.title

        }
    }
}
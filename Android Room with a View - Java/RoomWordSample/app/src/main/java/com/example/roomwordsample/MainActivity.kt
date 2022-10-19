package com.example.roomwordsample

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomwordsample.data.Word
import com.example.roomwordsample.data.WordRoomDatabase
import com.example.roomwordsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val wordViewModel: WordViewModel by viewModels{
        WordViewModelFatory(
            (application as BaseApplication).database.wordDao()
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = WordListAdapter { word ->
            Log.d("TAG", word.word)
        }
        binding.recyclerview.adapter = adapter


        wordViewModel.getAllWords().observe(this) {
            adapter.submitList(it)
        }
    }


}
package com.example.roomwordsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomwordsample.data.Word
import com.example.roomwordsample.databinding.RecyclerviewItemBinding

class WordListAdapter(val onItemClick: (Word)-> Unit): ListAdapter<Word,WordListAdapter.WordViewHolder>(DiffCallback) {
    companion object  {
        private val DiffCallback = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.word.equals(newItem.word)
            }

        }
    }

    class WordViewHolder(private var binding: RecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(word: Word){
                binding.word = word
                binding.executePendingBindings()
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(RecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false
        ))
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        var current = getItem(position)
        holder.bind(current)
        holder.itemView.setOnClickListener(){
            onItemClick(current)
        }
    }
}
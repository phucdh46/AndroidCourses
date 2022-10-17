package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class DiffWord(val oldWords: LinkedList<String>, val newWords: LinkedList<String>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldWords.size
    override fun getNewListSize() = newWords.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldWords.get(oldItemPosition) == newWords.get(newItemPosition)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldWords.get(oldItemPosition) === newWords.get(newItemPosition)

    }


}

class WordListAdapter(val mWordList: LinkedList<String>, private val onItemClicked: (Int) -> Unit) :
    RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    fun submitData(temp: LinkedList<String>) {
        val diffWord = DiffUtil.calculateDiff(DiffWord(mWordList, temp))
        mWordList.clear()
        mWordList.addAll(temp)
        diffWord.dispatchUpdatesTo(this)

    }

    class WordViewHolder(val view: View, val adapter: WordListAdapter) :
        RecyclerView.ViewHolder(view) {
        val wordItemView = view.findViewById<TextView>(R.id.word)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.wordlist_item, parent, false)
        return WordViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val mCurrent = mWordList.get(position)
        holder.wordItemView.text = mCurrent
        holder.itemView.setOnClickListener() {
            onItemClicked(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = mWordList.size


}
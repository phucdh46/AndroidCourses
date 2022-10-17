package com.example.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        val mWordList: LinkedList<String> = LinkedList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (i in 0..19) {
            mWordList.addLast("Word $i")
        }

        val mRecyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        val mAdapter = WordListAdapter(mWordList) {
            mWordList.set(it, "Clicked! $it")

        }
        //mAdapter.submitData(mWordList)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener() {
            val wordListSize = mWordList.size
            mWordList.add("+ Word $wordListSize")
            mAdapter.notifyItemInserted(wordListSize)
            mRecyclerView.smoothScrollToPosition(wordListSize)
        }
    }
}
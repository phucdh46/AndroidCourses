package com.example.clickableimages

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        val message = intent.getStringExtra(MainAcivity.EXTRA_MESSAGE)
        val tv = findViewById<TextView>(R.id.tvMessage)
        tv.text = message
    }
}
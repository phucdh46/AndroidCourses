package com.example.clickableimages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainAcivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MESSAGE = "com.example.android.droidcafe.extra.MESSAGE"

    }

    private var mOrderMessage: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener() {
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra(EXTRA_MESSAGE, mOrderMessage)
            startActivity(intent)
        }
    }

    fun showDonutOrder(view: View) {
        mOrderMessage = getString(R.string.donut_order_message);
        displayToast(mOrderMessage!!)
    }

    fun showIceCreamOrder(view: View) {
        mOrderMessage = getString(R.string.ice_cream_message);
        displayToast(mOrderMessage!!)
    }

    fun showFroyoOrder(view: View) {
        mOrderMessage = getString(R.string.froyo_message);
        displayToast(mOrderMessage!!)
    }

    private fun displayToast(mOrderMessage: String) {
        Toast.makeText(this, mOrderMessage, Toast.LENGTH_SHORT).show()
    }
}
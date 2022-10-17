package com.example.clickableimages

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener() {
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra(EXTRA_MESSAGE, mOrderMessage)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> displayToast(getString(R.string.action_order_message))
            R.id.action_contact -> displayToast(getString(R.string.action_contact_message))
            R.id.action_favorites -> displayToast(getString(R.string.action_favorites_message))
            R.id.action_status -> displayToast(getString(R.string.action_status_message))
        }

        return super.onOptionsItemSelected(item)
    }

    fun showDonutOrder(view: View) {
        mOrderMessage = getString(R.string.donut_order_message)
        displayToast(mOrderMessage!!)
    }

    fun showIceCreamOrder(view: View) {
        mOrderMessage = getString(R.string.ice_cream_message)
        displayToast(mOrderMessage!!)
    }

    fun showFroyoOrder(view: View) {
        mOrderMessage = getString(R.string.froyo_message)
        displayToast(mOrderMessage!!)
    }

    private fun displayToast(mOrderMessage: String) {
        Toast.makeText(this, mOrderMessage, Toast.LENGTH_SHORT).show()
    }
}
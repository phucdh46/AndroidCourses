package com.example.broadcastreceive

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {
    val mReceiver = CustomReceiver()

    companion object {
        const val ACTION_CUSTOM_BROADCAST: String = "ACTION_CUSTOM_BROADCAST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        filter.addAction(Intent.ACTION_HEADSET_PLUG)
        this.registerReceiver(mReceiver, filter);

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(
                mReceiver,
                IntentFilter(ACTION_CUSTOM_BROADCAST)
            );
    }

    override fun onDestroy() {
        this.unregisterReceiver(mReceiver)
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mReceiver);
        super.onDestroy()
    }

    fun sendCustomBroadcast(view: View) {
        val customBroadcastIntent = Intent(ACTION_CUSTOM_BROADCAST)
        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);

    }
}
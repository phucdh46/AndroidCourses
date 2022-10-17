package com.example.broadcastreceive

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CustomReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val intentAction = intent.action
        if (intentAction != null) {
            var message: String? = null
            when (intentAction) {
                Intent.ACTION_POWER_CONNECTED -> {
                    message = "Power connected!"
                }
                Intent.ACTION_POWER_DISCONNECTED -> {
                    message = "Power disconnected!"
                }
                MainActivity.ACTION_CUSTOM_BROADCAST -> {
                    message = "ACTION_CUSTOM_BROADCAST"
                }
                Intent.ACTION_HEADSET_PLUG -> {
                    message = "HEADSET_PLUG"

                }
                else -> message = "unknown intent action"
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
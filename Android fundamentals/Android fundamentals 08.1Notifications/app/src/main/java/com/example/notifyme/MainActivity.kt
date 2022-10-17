package com.example.notifyme

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {
    companion object {
        const val ACTION_UPDATE_NOTIFICATION =
            "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION"
        const val ACTION_DISMISS_NOTIFICATION =
            "ACTION_DISMISS_NOTIFICATION"
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        const val NOTIFICATION_ID = 0
    }

    val mReceiver = NotificationReceiver()
    var mNotifyManager: NotificationManager? = null
    var btn_notify: Button? = null
    var button_cancel: Button? = null
    var button_update: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNotificationButtonState(true, false, false)
        btn_notify = findViewById<Button>(R.id.notify)
        button_cancel = findViewById<Button>(R.id.cancel)
        button_update = findViewById<Button>(R.id.update)
        btn_notify?.setOnClickListener() {
            sendNotification()
        }

        button_cancel?.setOnClickListener() {
            cancelNotification()
        }

        button_update?.setOnClickListener() {
            updateNotification()
        }
        registerReceiver(mReceiver, IntentFilter(ACTION_UPDATE_NOTIFICATION));

    }

    override fun onDestroy() {
        this.unregisterReceiver(mReceiver)
        super.onDestroy()
    }

    fun updateNotification() {

        val androidImage = BitmapFactory
            .decodeResource(resources, R.drawable.mascot_1)
        val notifyBuilder = getNotificationBuilder()
        notifyBuilder.setStyle(
            NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification Updated!")
        )
        mNotifyManager?.notify(NOTIFICATION_ID, notifyBuilder.build())
        setNotificationButtonState(false, false, true);

    }

    fun cancelNotification() {
        mNotifyManager?.cancel(NOTIFICATION_ID)
        setNotificationButtonState(true, false, false)
    }

    private fun sendNotification() {

        val dismissIntent = Intent(ACTION_DISMISS_NOTIFICATION)
        val dismissPendingIntent = PendingIntent.getBroadcast(
            this,
            NOTIFICATION_ID,
            dismissIntent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val updateIntent = Intent(ACTION_UPDATE_NOTIFICATION)
        val updatePendingIntent = PendingIntent.getBroadcast(
            this,
            NOTIFICATION_ID,
            updateIntent,
            PendingIntent.FLAG_ONE_SHOT
        )

        createNotificationChannel()

        val notificationIntent = Intent(this, MainActivity::class.java)
        val notificationPendingIntent = PendingIntent.getActivity(
            this,
            NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notifyBuilder = getNotificationBuilder()
            .setContentIntent(notificationPendingIntent)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_update, "Update Notification", updatePendingIntent)
        notifyBuilder.setDeleteIntent(dismissPendingIntent)

        mNotifyManager?.notify(NOTIFICATION_ID, notifyBuilder.build())

        setNotificationButtonState(false, true, true);

    }

    private fun createNotificationChannel() {
        mNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >=
            android.os.Build.VERSION_CODES.O
        ) {
            // Create a NotificationChannel
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Mascot Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = "Notification from Mascot"

            }
            mNotifyManager?.createNotificationChannel(notificationChannel)
        }
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder {
        val notifyBuilder = NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
            .setContentTitle("You've been notified!")
            .setContentText("This is your notification text.")
            .setSmallIcon(R.drawable.ic_android)

        return notifyBuilder

    }

    fun setNotificationButtonState(
        isNotifyEnabled: Boolean,
        isUpdateEnabled: Boolean,
        isCancelEnabled: Boolean
    ) {
        btn_notify?.setEnabled(isNotifyEnabled)
        button_update?.setEnabled(isUpdateEnabled)
        button_cancel?.setEnabled(isCancelEnabled)
    }

    inner class NotificationReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
            //
            when (intent.action) {
                ACTION_DISMISS_NOTIFICATION -> {
                    Log.d("TAG", "dismiss")
                    setNotificationButtonState(true, false, false)
                }
                /* ACTION_UPDATE_NOTIFICATION ->{
                     updateNotification()

                 }*/
            }
        }
    }
}


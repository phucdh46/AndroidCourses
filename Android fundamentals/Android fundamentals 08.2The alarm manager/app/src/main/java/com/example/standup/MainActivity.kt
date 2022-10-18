package com.example.standup

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {
    companion object {
        const val NOTIFICATION_ID = 0
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }

    var mNotificationManager: NotificationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val notifyIntent = Intent(this, AlarmReceiver::class.java)
        val notifyPendingIntent = PendingIntent.getActivity(
            this,
            NOTIFICATION_ID,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val alarmToggle = findViewById<ToggleButton>(R.id.alarmToggle)
        alarmToggle.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(compoundButton: CompoundButton?, isChecked: Boolean) {
                var message: String? = null
                val repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES
                val triggerTime = (SystemClock.elapsedRealtime()
                        + repeatInterval)


                if (isChecked) {
                    deliverNotification(this@MainActivity)
                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating(
                            AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            triggerTime, repeatInterval, notifyPendingIntent
                        )
//                        Toast.makeText(this@MainActivity,   alarmManager.nextAlarmClock.toString(), Toast.LENGTH_SHORT)
//                            .show()
                    }

                    message = getString(R.string.alarm_on)
                } else {
                    if (alarmManager != null) {
                        alarmManager.cancel(notifyPendingIntent);
                    }
                    message = getString(R.string.alarm_off)
                    mNotificationManager?.cancelAll()
                }

                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                    .show()
            }

        })
        val alarmUp = PendingIntent.getBroadcast(
            this, NOTIFICATION_ID, notifyIntent,
            PendingIntent.FLAG_NO_CREATE
        ) != null
        alarmToggle.setChecked(alarmUp)

    }

    fun createNotificationChannel() {
        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >=
            android.os.Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                getString(R.string.name_channel),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = getString(R.string.description_notification_channel)
            }
            mNotificationManager?.createNotificationChannel(notificationChannel)

        }
    }

    fun deliverNotification(context: Context) {
        createNotificationChannel()
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        val builder = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_action_name)
            .setContentTitle("Stand Up Alert")
            .setContentText("You should stand up and walk around now!")
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        mNotificationManager?.notify(NOTIFICATION_ID, builder.build())
    }

    inner class AlarmReceiver : android.content.BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            mNotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            deliverNotification(context)
        }

    }
}
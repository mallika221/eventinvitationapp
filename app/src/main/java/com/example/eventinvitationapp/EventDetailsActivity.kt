package com.example.eventinvitationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class EventDetailsActivity : AppCompatActivity() {

    private val CHANNEL_ID = "event_notification_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        val eventName = intent.getStringExtra("EVENT_NAME")
        val eventDate = intent.getStringExtra("EVENT_DATE")
        val eventLocation = intent.getStringExtra("EVENT_LOCATION")

        val eventDetailsText = findViewById<TextView>(R.id.eventDetailsText)
        eventDetailsText.text = "Event: $eventName\nDate: $eventDate\nLocation: $eventLocation"

        createNotificationChannel()

        // Create an intent for the notification tap action
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Build the notification
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_event)
            .setContentTitle("Event Reminder")
            .setContentText("Don't forget your event: $eventName on $eventDate at $eventLocation")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0, 500, 500, 500))
            .setLights(0xFF00FF00.toInt(), 300, 1000)

        // Show the notification
        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }

    // Create the NotificationChannel (needed for Android 8.0+)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Event Notification"
            val descriptionText = "Channel for Event Notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

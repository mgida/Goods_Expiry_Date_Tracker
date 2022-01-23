package com.example.goods_expiry_date_tracker.notifaction

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.goods_expiry_date_tracker.R
import com.example.goods_expiry_date_tracker.utils.Constant.Companion.CHANNEL_ID
import com.example.goods_expiry_date_tracker.utils.Constant.Companion.NOTIFICATION_ID

class NotificationHelper {

    fun displayNotification(context: Context, msg: String) {
        createNotificationChannel(context)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Expiration!")
            .setContentText(msg)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(context)
            .notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val des = "Channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channels = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = des
            }
            val notificationManger =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManger.createNotificationChannel(channels)
        }
    }
}
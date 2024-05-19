package com.example.musickotlin

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class ApplicationClass: Application() {
    companion object{
        const val CHANNEL_ID="channel1"
        const val PLAY ="paly"
        const val NEXT="next"
        const val PREV ="prev"
        const val EXIT="exit"
    }
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val notificationchannel=NotificationChannel(CHANNEL_ID,"now playing",NotificationManager.IMPORTANCE_DEFAULT)
            notificationchannel.description="description"
            val notificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationchannel)
        }
    }
}
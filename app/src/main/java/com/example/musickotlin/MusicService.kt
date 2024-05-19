package com.example.musickotlin

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.widget.Toast
import androidx.core.app.NotificationCompat


class MusicService:Service() {
    private var myBinder=MyBinder()
    var mediaPlayer:MediaPlayer?=null
    private  lateinit var mediasession:MediaSessionCompat
    override fun onBind(intent: Intent?): IBinder? {
        mediasession= MediaSessionCompat(baseContext,"My Music")
        return myBinder
    }
    inner class MyBinder:Binder(){
        fun currentService():MusicService{
            return this@MusicService
        }

    }

    fun showNotification(playpausebtn:Int){
        fun getNotiImg(path:String):ByteArray?{
            val retriever=MediaMetadataRetriever()
            retriever.setDataSource(path)
            return retriever.embeddedPicture
        }
        val notiImg=getNotiImg(PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos).path)
        val img = if (notiImg!=null){
            BitmapFactory.decodeByteArray(notiImg,0,notiImg.size)
        }else{
            BitmapFactory.decodeResource(resources,R.drawable.round_music_note_24)
        }
        val notificationclickintent = Intent(baseContext,MainActivity::class.java)
        val notclickpending=PendingIntent.getActivity(this,0,notificationclickintent,PendingIntent.FLAG_MUTABLE)
        val prevIntent=Intent(baseContext,Notificationreceiver::class.java).setAction(ApplicationClass.PREV)
        val prevPendingIntent=PendingIntent.getBroadcast(baseContext,0,prevIntent,PendingIntent.FLAG_MUTABLE)
        val nextIntent=Intent(baseContext,Notificationreceiver::class.java).setAction(ApplicationClass.NEXT)
        val nextPendingIntent=PendingIntent.getBroadcast(baseContext,0,nextIntent,PendingIntent.FLAG_MUTABLE)
        val playIntent=Intent(baseContext,Notificationreceiver::class.java).setAction(ApplicationClass.PLAY)
        val playPendingIntent=PendingIntent.getBroadcast(baseContext,0,playIntent,PendingIntent.FLAG_MUTABLE)
        val exitIntent=Intent(baseContext,Notificationreceiver::class.java).setAction(ApplicationClass.EXIT)
        val exitPendingIntent=PendingIntent.getBroadcast(baseContext,0,exitIntent,PendingIntent.FLAG_MUTABLE)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this,ApplicationClass.CHANNEL_ID)
            .setContentIntent(notclickpending)
            .setContentTitle(PlayerActivity.playeractivityMusicList[PlayerActivity.songpos].title)
            .setSmallIcon(R.drawable.round_music_note_24)
            .setContentText(PlayerActivity.playeractivityMusicList[PlayerActivity.songpos].artist)
            .setLargeIcon(img)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.back_img,"Previous",prevPendingIntent)
            .addAction(playpausebtn,"Play",playPendingIntent)
            .addAction(R.drawable.next_img,"Next",nextPendingIntent)
            .addAction(R.drawable.exit_img,"Previous",exitPendingIntent)
            .build()
        notificationManager.notify(1,notification)
        startForeground(1,notification)
    }
}
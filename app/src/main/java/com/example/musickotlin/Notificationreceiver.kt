package com.example.musickotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlin.system.exitProcess

class Notificationreceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            ApplicationClass.PREV-> {context?.let { prevSong(it) }}
            ApplicationClass.PLAY-> {if (PlayerActivity.isplaying){pauseMusic()}else{playMusic()}}
            ApplicationClass.NEXT-> {context?. let { nextSong(context) }}
            ApplicationClass.EXIT-> {
                PlayerActivity.musicservice!!.stopForeground(true)
                PlayerActivity.musicservice=null
                exitProcess(1)
            }
        }
    }
    private fun playMusic(){
        PlayerActivity.isplaying=true
        PlayerActivity.musicservice!!.mediaPlayer!!.start()
        PlayerActivity.musicservice!!.showNotification(R.drawable.pause_img)
        PlayerActivity.binding.playbutton.setImageResource(R.drawable.pause_img)
    }
    private fun pauseMusic(){
        PlayerActivity.isplaying=false
        PlayerActivity.musicservice!!.mediaPlayer!!.pause()
        PlayerActivity.musicservice!!.showNotification(R.drawable.play_img)
        PlayerActivity.binding.playbutton.setImageResource(R.drawable.play_img)
    }
    private fun nextSong(context:Context){
        if (PlayerActivity.songpos == PlayerActivity.playeractivityMusicList.size-1){
            PlayerActivity.songpos =0
        }else{
            PlayerActivity.songpos = PlayerActivity.songpos +1
        }
        if (PlayerActivity.playeractivityisactive){
            context.let { PlayerActivity.setLayout(it) }
        }
        if (PlayerActivity.musicservice!!.mediaPlayer==null){
            PlayerActivity.musicservice!!.mediaPlayer= MediaPlayer()
            PlayerActivity.musicservice!!.mediaPlayer!!.reset()
            PlayerActivity.musicservice!!.mediaPlayer!!.setDataSource(
                PlayerActivity.playeractivityMusicList.get(
                    PlayerActivity.songpos
                ).path)
            PlayerActivity.musicservice!!.mediaPlayer!!.prepare()
            PlayerActivity.musicservice!!.mediaPlayer!!.start()
            PlayerActivity.isplaying =true
            PlayerActivity.binding.playbutton.setImageResource(R.drawable.pause_img)
            PlayerActivity.musicservice!!.showNotification(R.drawable.pause_img)
                val image:ByteArray?=MainActivity.getAlbumArt(
                    PlayerActivity.playeractivityMusicList.get(
                        PlayerActivity.songpos
                    ).path)
                if (image != null) {
                    Glide.with(context).asBitmap().load(image).into(NowPlaying.binding.nowplayingimage)
                } else {
                    Glide.with(context).load(R.drawable.round_music_note_24).into(NowPlaying.binding.nowplayingimage)
                }
            NowPlaying.binding.nowplayingtext.text=PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos).title
        }else{
//            mediaplayer= MediaPlayer()
            PlayerActivity.musicservice!!.mediaPlayer!!.reset()
            PlayerActivity.musicservice!!.mediaPlayer!!.setDataSource(
                PlayerActivity.playeractivityMusicList.get(
                    PlayerActivity.songpos
                ).path)
            PlayerActivity.musicservice!!.mediaPlayer!!.prepare()
            PlayerActivity.musicservice!!.mediaPlayer!!.start()
            PlayerActivity.isplaying =true
            PlayerActivity.binding.playbutton.setImageResource(R.drawable.pause_img)
            PlayerActivity!!.musicservice!!.showNotification(R.drawable.pause_img)
                val image:ByteArray?=MainActivity.getAlbumArt(
                    PlayerActivity.playeractivityMusicList.get(
                        PlayerActivity.songpos
                    ).path)
                if (image != null) {
                    Glide.with(context).asBitmap().load(image).into(NowPlaying.binding.nowplayingimage)
                } else {
                    Glide.with(context).load(R.drawable.round_music_note_24).into(NowPlaying.binding.nowplayingimage)
                }
            NowPlaying.binding.nowplayingtext.text=PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos).title
        }
    }

    private fun prevSong(context:Context){
        if (PlayerActivity.songpos == 0){
            PlayerActivity.songpos =0
        }else{
            PlayerActivity.songpos = PlayerActivity.songpos -1
        }
        context.let { PlayerActivity.setLayout(it) }
        if (PlayerActivity.musicservice!!.mediaPlayer==null){
            PlayerActivity.musicservice!!.mediaPlayer= MediaPlayer()
            PlayerActivity.musicservice!!.mediaPlayer!!.reset()
            PlayerActivity.musicservice!!.mediaPlayer!!.setDataSource(
                PlayerActivity.playeractivityMusicList.get(
                    PlayerActivity.songpos
                ).path)
            PlayerActivity.musicservice!!.mediaPlayer!!.prepare()
            PlayerActivity.musicservice!!.mediaPlayer!!.start()
            PlayerActivity.isplaying =true
            PlayerActivity.binding.playbutton.setImageResource(R.drawable.pause_img)
            PlayerActivity!!.musicservice!!.showNotification(R.drawable.pause_img)
            val image:ByteArray?=MainActivity.getAlbumArt(
                PlayerActivity.playeractivityMusicList.get(
                    PlayerActivity.songpos
                ).path)
            if (image != null) {
                Glide.with(context).asBitmap().load(image).into(NowPlaying.binding.nowplayingimage)
            } else {
                Glide.with(context).load(R.drawable.ic_launcher_foreground).into(NowPlaying.binding.nowplayingimage)
            }
            NowPlaying.binding.nowplayingtext.text=PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos).title
        }else{
//            mediaplayer= MediaPlayer()
            PlayerActivity.musicservice!!.mediaPlayer!!.reset()
            PlayerActivity.musicservice!!.mediaPlayer!!.setDataSource(
                PlayerActivity.playeractivityMusicList.get(
                    PlayerActivity.songpos
                ).path)
            PlayerActivity.musicservice!!.mediaPlayer!!.prepare()
            PlayerActivity.musicservice!!.mediaPlayer!!.start()
            PlayerActivity.isplaying =true
            PlayerActivity.binding.playbutton.setImageResource(R.drawable.pause_img)
            PlayerActivity!!.musicservice!!.showNotification(R.drawable.pause_img)
            val image:ByteArray?=MainActivity.getAlbumArt(
                PlayerActivity.playeractivityMusicList.get(
                    PlayerActivity.songpos
                ).path)
            if (image != null) {
                Glide.with(context).asBitmap().load(image).into(NowPlaying.binding.nowplayingimage)
            } else {
                Glide.with(context).load(R.drawable.ic_launcher_foreground).into(NowPlaying.binding.nowplayingimage)
            }
            NowPlaying.binding.nowplayingtext.text=PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos).title
        }
    }
}
package com.example.musickotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.musickotlin.databinding.FragmentNowPlayingBinding


class NowPlaying : Fragment() {

    override fun onPause() {
        super.onPause()
        nowplayingactivityactive=false
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var binding : FragmentNowPlayingBinding
        var nowplayingactivityactive:Boolean=false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)
        binding= FragmentNowPlayingBinding.bind(view)
        binding.root.visibility=View.INVISIBLE
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (PlayerActivity.musicservice!=null){
            nowplayingactivityactive=true
            binding.root.visibility=View.VISIBLE
            if (PlayerActivity.musicservice!!.mediaPlayer!!.isPlaying){
                binding.nowplayingplaypause.setImageResource(R.drawable.pause_img)
            }
            binding.nowplayingtext.isSelected=true
            val image:ByteArray?=MainActivity.getAlbumArt(
                PlayerActivity.playeractivityMusicList.get(
                    PlayerActivity.songpos
                ).path)
            if (image != null) {
                Glide.with(this).asBitmap().load(image).into(binding.nowplayingimage)
            } else {
                Glide.with(this).load(R.drawable.round_music_note_24).into(binding.nowplayingimage)
            }
            binding.nowplayingtext.text = PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos).title
            binding.nowplayingnext.setOnClickListener {
                if (PlayerActivity.songpos == PlayerActivity.playeractivityMusicList.size-1){
                    PlayerActivity.songpos =0
                }else{
                    PlayerActivity.songpos = PlayerActivity.songpos +1
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
                        Glide.with(this).asBitmap().load(image).into(NowPlaying.binding.nowplayingimage)
                    } else {
                        Glide.with(this).load(R.drawable.round_music_note_24).into(NowPlaying.binding.nowplayingimage)
                    }
                    binding.nowplayingtext.text=PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos).title
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
                        Glide.with(this).asBitmap().load(image).into(NowPlaying.binding.nowplayingimage)
                    } else {
                        Glide.with(this).load(R.drawable.round_music_note_24).into(NowPlaying.binding.nowplayingimage)
                    }
                    binding.nowplayingtext.text=PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos).title
                }
            }
            binding.nowplayingprev.setOnClickListener {
                if (PlayerActivity.songpos == 0){
                    PlayerActivity.songpos =0
                }else{
                    PlayerActivity.songpos = PlayerActivity.songpos -1
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
                    PlayerActivity!!.musicservice!!.showNotification(R.drawable.pause_img)
                    val image:ByteArray?=MainActivity.getAlbumArt(
                        PlayerActivity.playeractivityMusicList.get(
                            PlayerActivity.songpos
                        ).path)
                    if (image != null) {
                        Glide.with(this).asBitmap().load(image).into(NowPlaying.binding.nowplayingimage)
                    } else {
                        Glide.with(this).load(R.drawable.round_music_note_24).into(NowPlaying.binding.nowplayingimage)
                    }
                    binding.nowplayingtext.text=PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos).title
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
                        Glide.with(this).asBitmap().load(image).into(NowPlaying.binding.nowplayingimage)
                    } else {
                        Glide.with(this).load(R.drawable.round_music_note_24).into(NowPlaying.binding.nowplayingimage)
                    }
                    binding.nowplayingtext.text=PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos).title
                }
            }
            binding.nowplayingtext.setOnClickListener{
                val intent = Intent(requireContext(),PlayerActivity::class.java)
                intent.putExtra("position",PlayerActivity.songpos)
                intent.putExtra("class","NowPlaying")
                ContextCompat.startActivity(requireContext(),intent,null)
            }
            binding.nowplayingplaypause.setOnClickListener {
                if (PlayerActivity.musicservice!!.mediaPlayer!!.isPlaying){
                    PlayerActivity.musicservice!!.mediaPlayer!!.pause()
                    PlayerActivity.isplaying=false
                    PlayerActivity.musicservice!!.showNotification(R.drawable.play_img)
                    binding.nowplayingplaypause.setImageResource(R.drawable.play_img)

                }else{
                    PlayerActivity.musicservice!!.mediaPlayer!!.start()
                    PlayerActivity.isplaying=true
                    PlayerActivity.musicservice!!.showNotification(R.drawable.pause_img)
                    binding.nowplayingplaypause.setImageResource(R.drawable.pause_img)
                }
            }
        }
    }

}
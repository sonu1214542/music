package com.example.musickotlin

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.example.music.PlaylistAdapter
import com.example.music.PlaylistsFragment
import com.example.musickotlin.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(),ServiceConnection,MediaPlayer.OnCompletionListener {
    private lateinit var runnable:Runnable
    companion object{
        var playeractivityisactive:Boolean = false
        lateinit var playeractivityMusicList: ArrayList<MusicClass>
        lateinit var playlistname:String
        var songpos:Int=0
        //var mediaplayer:MediaPlayer?=null
        var isplaying:Boolean=false
        var musicservice :MusicService?=null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityPlayerBinding
        fun setLayout(context:Context){
            val image:ByteArray?=MainActivity.getAlbumArt(playeractivityMusicList.get(songpos).path)
            if (image != null) {
                Glide.with(context).asBitmap().load(image).into(binding.playeractivityalbumart)
            } else {
                Glide.with(context).load(R.drawable.round_music_note_24).into(binding.playeractivityalbumart)
            }
            binding.playeractivitysongname.text= playeractivityMusicList.get(songpos).title
        }
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    fun addToPlaylist(){
        try {
            val dialog = PlayListDialog()
            dialog.show(
                supportFragmentManager,
                "playlist dialog"
            )
            if (PlaylistsFragment.playlistAdapter != null) {
                PlaylistsFragment.playlistAdapter!!.notifyDataSetChanged()
            }
            Toast.makeText(
                this@PlayerActivity,
                PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos).title,
                Toast.LENGTH_SHORT
            )
                .show()
        } catch (e: Exception) {
            Log.e(
                "PlayerActivity",
                "Error adding song to playlist: " + e.message
            )
            Toast.makeText(
                this@PlayerActivity,
                e.message,
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        playeractivityisactive=true
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initlayout()
        binding.playbutton.setOnClickListener{if (isplaying){pausemusic()}else{playmusic()}}
        binding.nextbutton.setOnClickListener { playnextsong(true) }
        binding.addtoplaylistbutton.setOnClickListener { addToPlaylist() }
        binding.prevbutton.setOnClickListener { playnextsong(false) }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                try {
                    if (fromUser){
                        musicservice!!.mediaPlayer!!.seekTo(progress)
                    }
                }catch (e:Exception){
                    Log.e("error", e.toString())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Unit
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Unit
            }
        })
//        binding.startduration.text= formatsongDuration(musicservice!!.mediaPlayer!!.currentPosition.toLong())
//        binding.totalduration.text= formatsongDuration(musicservice!!.mediaPlayer!!.duration.toLong())
//        binding.seekBar.progress=0
//        binding.seekBar.max= musicservice!!.mediaPlayer!!.duration
    }

    private fun initlayout(){
        songpos=intent.getIntExtra("position",0)
        when(intent.getStringExtra("class")){
            "albumsongs"->{
                val intent=Intent(this,MusicService::class.java)
                bindService(intent,this, BIND_AUTO_CREATE)
                startService(intent)
                playeractivityMusicList= ArrayList()
                playeractivityMusicList.addAll(AlbumSongs.albumsongs)
                setLayout(this)
            }
            "playlistsongs"->{
                val intent=Intent(this,MusicService::class.java)
                bindService(intent,this, BIND_AUTO_CREATE)
                startService(intent)
                playeractivityMusicList= ArrayList()
                Toast.makeText(this,PlaylistSongs.Playlistsongs.toString(),Toast.LENGTH_SHORT).show()
                playeractivityMusicList= PlaylistSongs.Playlistsongs!!
                setLayout(this)
            }
            "NowPlaying"->{
                setLayout(this)
                binding.startduration.text= formatsongDuration(musicservice!!.mediaPlayer!!.currentPosition.toLong())
                binding.totalduration.text= formatsongDuration(musicservice!!.mediaPlayer!!.duration.toLong())
                binding.seekBar.progress= musicservice!!.mediaPlayer!!.currentPosition
                binding.seekBar.max= musicservice!!.mediaPlayer!!.duration
            }
            "songadapter"->{
                val intent=Intent(this,MusicService::class.java)
                bindService(intent,this, BIND_AUTO_CREATE)
                startService(intent)
                playeractivityMusicList= ArrayList()
                playeractivityMusicList.addAll(MainActivity.MusiclistMainactivity)
                setLayout(this)
                //createmediaplayer()
            }
        }
    }

    private fun createmediaplayer(){
        if (musicservice!!.mediaPlayer==null){
            musicservice!!.mediaPlayer= MediaPlayer()
            musicservice!!.mediaPlayer!!.reset()
            musicservice!!.mediaPlayer!!.setDataSource(playeractivityMusicList.get(songpos).path)
            musicservice!!.mediaPlayer!!.prepare()
            if (playeractivityisactive){
                binding.startduration.text= formatsongDuration(musicservice!!.mediaPlayer!!.currentPosition.toLong())
                binding.totalduration.text= formatsongDuration(musicservice!!.mediaPlayer!!.duration.toLong())
                binding.seekBar.progress=0
                binding.seekBar.max= musicservice!!.mediaPlayer!!.duration
                binding.playbutton.setImageResource(R.drawable.pause_img)
            }
            musicservice!!.mediaPlayer!!.start()
            isplaying=true
            musicservice!!.mediaPlayer!!.setOnCompletionListener ( this )
        }else{
//            mediaplayer= MediaPlayer()
            musicservice!!.mediaPlayer!!.reset()
            musicservice!!.mediaPlayer!!.setDataSource(playeractivityMusicList.get(songpos).path)
            musicservice!!.mediaPlayer!!.prepare()
            if (playeractivityisactive){
                binding.startduration.text= formatsongDuration(musicservice!!.mediaPlayer!!.currentPosition.toLong())
                binding.totalduration.text= formatsongDuration(musicservice!!.mediaPlayer!!.duration.toLong())
                binding.seekBar.progress=0
                binding.seekBar.max= musicservice!!.mediaPlayer!!.duration
                binding.playbutton.setImageResource(R.drawable.pause_img)
            }
            musicservice!!.mediaPlayer!!.start()
            isplaying=true
            musicservice!!.mediaPlayer!!.setOnCompletionListener ( this )
        }
    }


    private fun playmusic(){
        binding.playbutton.setImageResource(R.drawable.pause_img)
        musicservice!!.showNotification(R.drawable.pause_img)
        musicservice!!.mediaPlayer!!.start()
        isplaying=true
    }

    private fun pausemusic(){
        binding.playbutton.setImageResource(R.drawable.play_img)
        musicservice!!.showNotification(R.drawable.play_img)
        musicservice!!.mediaPlayer!!.pause()
        isplaying=false
    }

    fun playnextsong(increment:Boolean){
        if (increment){
            if (songpos==playeractivityMusicList.size-1){
                songpos=0
            }else{
                songpos= songpos+1
            }
            if (playeractivityisactive){
                setLayout(this)
            }
            createmediaplayer()
            musicservice!!.showNotification(R.drawable.pause_img)
                if (NowPlaying.nowplayingactivityactive){
                    NowPlaying.binding.nowplayingtext.text= playeractivityMusicList.get(songpos).title
                    val image:ByteArray?=MainActivity.getAlbumArt(
                        PlayerActivity.playeractivityMusicList.get(
                            PlayerActivity.songpos
                        ).path)
                    if (image != null) {
                        Glide.with(NowPlaying.binding.nowplayingimage.context).asBitmap().load(image).into(NowPlaying.binding.nowplayingimage)
                    } else {
                        Glide.with(NowPlaying.binding.nowplayingimage.context).load(R.drawable.round_music_note_24).into(NowPlaying.binding.nowplayingimage)
                    }
                }
        }else{
            if (songpos==0){
                songpos=0
            }else{
                songpos= songpos-1
            }
                setLayout(this)
            createmediaplayer()
            musicservice!!.showNotification(R.drawable.pause_img)
            if (NowPlaying.nowplayingactivityactive){
                NowPlaying.binding.nowplayingtext.text= playeractivityMusicList.get(songpos).title
                val image:ByteArray?=MainActivity.getAlbumArt(
                    PlayerActivity.playeractivityMusicList.get(
                        PlayerActivity.songpos
                    ).path)
                if (image != null) {
                    Glide.with(NowPlaying.binding.nowplayingimage.context).asBitmap().load(image).into(NowPlaying.binding.nowplayingimage)
                } else {
                    Glide.with(NowPlaying.binding.nowplayingimage.context).load(R.drawable.round_music_note_24).into(NowPlaying.binding.nowplayingimage)
                }
            }
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_player)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder=service as MusicService.MyBinder
        musicservice=binder.currentService()
        createmediaplayer()
        musicservice!!.showNotification(R.drawable.pause_img)
        seekBarSetup()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicservice=null
    }

    fun seekBarSetup(){
        runnable = Runnable {
           binding.startduration.text= formatsongDuration(musicservice!!.mediaPlayer!!.currentPosition.toLong())
           binding.seekBar.progress= musicservice!!.mediaPlayer!!.currentPosition
           Handler(Looper.getMainLooper()).postDelayed(runnable,200)
       }
        Handler(Looper.getMainLooper()).postDelayed(runnable,0)
    }

    override fun onCompletion(mp: MediaPlayer?) {
            playnextsong(true)
    }

    override fun onPause() {
        super.onPause()
        playeractivityisactive=false
    }

    override fun onResume() {
        super.onResume()
        playeractivityisactive=true
    }
}
package com.example.musickotlin

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.music.PlaylistAdapter

class PlaylistSongs : AppCompatActivity() {
    var playlistSongsAdapter: PlaylistSongsAdapter? = null
    private var playlistname: String? = null
    var recyclerView: RecyclerView? = null
    var albumphoto: ImageView? = null
    var songname: String? = null
    companion object{
        var Playlistsongs: ArrayList<MusicClass>? = null
    }
    override fun onResume() {
        super.onResume()
        if (Playlistsongs!!.size >= 1) {
            playlistSongsAdapter = PlaylistSongsAdapter(this, Playlistsongs!!)
            recyclerView!!.adapter = playlistSongsAdapter
            recyclerView!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playlistname = intent.getStringExtra("playlistname")
        Playlistsongs = PlaylistAdapter.playlists.get(playlistname)
        setContentView(R.layout.fragment_song)
        recyclerView = findViewById<RecyclerView>(R.id.songsrv)
    }
}
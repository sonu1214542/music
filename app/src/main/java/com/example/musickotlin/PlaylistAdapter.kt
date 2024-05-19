package com.example.music

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musickotlin.MainActivity
import com.example.musickotlin.MusicClass
import com.example.musickotlin.PlaylistSongs
import com.example.musickotlin.R

// PlaylistAdapter.java
class PlaylistAdapter(private val pContext: Context, playlists: HashMap<String, ArrayList<MusicClass>>) : RecyclerView.Adapter<PlaylistAdapter.PlaylistView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistView {
        val view: View = LayoutInflater.from(pContext).inflate(R.layout.song_item, parent, false)
        return PlaylistView(view)
    }
    override fun onBindViewHolder(holder: PlaylistView, position: Int) {
        val playlistNames = ArrayList(playlists.keys)
        val playlistName = playlistNames[position]
        holder.playlistname.text = playlistName
        //ArrayList<MusicFiles> playlistsongs = playlists.get(playlistName);
        setPlaylistSongs(playlistName)
        val lastaddedsong: MusicClass = playlistsongs!![playlistsongs!!.size - 1]
        val image = MainActivity.getAlbumArt(lastaddedsong.path)
        if (image != null) {
            Glide.with(pContext).asBitmap().load(image).into(holder.playlistimage)
        } else {
            Glide.with(pContext).load(R.drawable.round_music_note_24).into(holder.playlistimage)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(pContext, PlaylistSongs::class.java)
            intent.putExtra("playlistname", playlistName)
            pContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

     class PlaylistView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var playlistimage: ImageView
        var playlistname: TextView

        init {
            playlistname = itemView.findViewById<TextView>(R.id.songname)
            playlistimage = itemView.findViewById<ImageView>(R.id.songimage)
        }
    }

    companion object {
        var playlistsongs: ArrayList<MusicClass>? = null
        var playlists: HashMap<String, ArrayList<MusicClass>> =
            HashMap<String, ArrayList<MusicClass>>()

        fun getPlaylists(): Set<String> {
            return playlists.keys
        }

        fun setPlaylistSongs(playlistName: String) {
            playlistsongs = playlists[playlistName]
        }
    }
}
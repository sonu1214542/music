package com.example.musickotlin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musickotlin.PlayerActivity
import com.example.musickotlin.PlaylistSongsAdapter.playlistSongsViewHolder

class PlaylistSongsAdapter(PlaylistSongsContext: Context, playlistsongs: ArrayList<MusicClass>) :
    RecyclerView.Adapter<playlistSongsViewHolder?>() {
    private val PlaylistSongsContext: Context
    private val playlistsongs: ArrayList<MusicClass>

    init {
        this.playlistsongs = playlistsongs
        this.PlaylistSongsContext = PlaylistSongsContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): playlistSongsViewHolder {
        val view: View =
            LayoutInflater.from(PlaylistSongsContext).inflate(R.layout.song_item, parent, false)
        return playlistSongsViewHolder(view)
    }

    override fun onBindViewHolder(holder: playlistSongsViewHolder, position: Int) {
        holder.songname.setText(playlistsongs[position].title)
        val image = MainActivity.getAlbumArt(playlistsongs[position].path)
        if (image != null) {
            Glide.with(PlaylistSongsContext).asBitmap().load(image).into(holder.songalbumart)
        } else {
            Glide.with(PlaylistSongsContext).load(R.drawable.round_music_note_24)
                .into(holder.songalbumart)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(PlaylistSongsContext, PlayerActivity::class.java)
            PlayerActivity.playeractivityMusicList = playlistsongs
            intent.putExtra("position", position)
            intent.putExtra("class","playlistsongs")
            intent.putExtra("playlistname", playlistname)
            ContextCompat.startActivity(PlaylistSongsContext,intent,null)
            //PlaylistSongsContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return playlistsongs.size
    }

    inner class playlistSongsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var songname: TextView
        var songalbumart: ImageView

        init {
            songname = itemView.findViewById<TextView>(R.id.songname)
            songalbumart = itemView.findViewById<ImageView>(R.id.songimage)
        }
    }

    companion object {
        var playlistname: String? = null
    }
}
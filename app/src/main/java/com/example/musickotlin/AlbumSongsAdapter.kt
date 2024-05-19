package com.example.musickotlin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musickotlin.databinding.AlbumItemBinding
import com.example.musickotlin.databinding.SongItemBinding

class AlbumSongsAdapter(private val context: Context,private var albumsongs:ArrayList<MusicClass>):
    RecyclerView.Adapter<AlbumSongsAdapter.AlbumsongsView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsongsView {
        return AlbumsongsView(SongItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: AlbumsongsView, position: Int) {
        val image:ByteArray?= MainActivity.getAlbumArt(albumsongs.get(position).path)
        if (image != null) {
            Glide.with(context).asBitmap().load(image).into(holder.albumimg)
        } else {
            Glide.with(context).load(R.drawable.round_music_note_24).into(holder.albumimg)
        }
        holder.songname.text= albumsongs.get(position).title
        holder.duration.text=formatsongDuration(albumsongs.get(position).duration)
        holder.itemView.setOnClickListener{
            var intent=Intent(context,PlayerActivity::class.java)
            intent.putExtra("position",position)
            intent.putExtra("class","albumsongs")
            ContextCompat.startActivity(context,intent,null)
            Toast.makeText(context,albumsongs.get(position).title, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return albumsongs.size
    }

    class AlbumsongsView(binding: SongItemBinding):RecyclerView.ViewHolder(binding.root){
        val albumimg=binding.songimage
        val songname =binding.songname
        val duration =binding.songduration
    }
}
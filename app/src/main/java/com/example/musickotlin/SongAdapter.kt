package com.example.musickotlin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.example.musickotlin.databinding.SongItemBinding

class SongAdapter(private val context:Context, private val musiclist: ArrayList<MusicClass>):RecyclerView.Adapter<SongAdapter.MyHolder>() {
    class MyHolder(binding: SongItemBinding): RecyclerView.ViewHolder(binding.root) {
         val songimage = binding.songimage
         val songname = binding.songname
         val artist = binding.artistname
         val duration=binding.songduration
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongAdapter.MyHolder {
        return MyHolder(SongItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: SongAdapter.MyHolder, position: Int) {
        val image:ByteArray?= MainActivity.getAlbumArt(musiclist[position].path)
        if (image != null) {
            Glide.with(context).asBitmap().load(image).into(holder.songimage)
        } else {
            Glide.with(context).load(R.drawable.round_music_note_24).into(holder.songimage)
        }
        holder.songname.text= musiclist[position].title
        holder.artist.text=musiclist[position].artist
        holder.itemView.setOnClickListener(View.OnClickListener { v: View? -> Toast.makeText(context, holder.songname.text, Toast.LENGTH_SHORT).show()
            val intent:Intent= Intent(context,PlayerActivity::class.java)
            intent.putExtra("position",position)
            intent.putExtra("class","songadapter")
            ContextCompat.startActivity(context,intent,null)
        })
        holder.duration.text= formatsongDuration(musiclist.get(position).duration)
    }

    override fun getItemCount(): Int {
        return musiclist.size
    }
}
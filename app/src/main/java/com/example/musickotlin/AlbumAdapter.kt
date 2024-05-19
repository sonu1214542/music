package com.example.musickotlin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musickotlin.databinding.AlbumItemBinding
import com.example.musickotlin.databinding.SongItemBinding

class AlbumAdapter(private val context: Context, private val albumlist: ArrayList<Album>):RecyclerView.Adapter<AlbumAdapter.AlbumHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumAdapter.AlbumHolder {
       return AlbumHolder(AlbumItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: AlbumAdapter.AlbumHolder, position: Int) {
        val image:ByteArray?= albumlist[position].albumArt
        if (image != null) {
            Glide.with(context).asBitmap().load(image).into(holder.albumimage)
        } else {
            Glide.with(context).load(R.drawable.round_music_note_24).into(holder.albumimage)
        }
        holder.albumname.text= albumlist[position].name
        holder.itemView.setOnClickListener{
            val intent = Intent(context,AlbumSongs::class.java)
            intent.putExtra("albumname",albumlist[position].name)
            ContextCompat.startActivity(context,intent,null)
        }
    }

    override fun getItemCount(): Int {
        return albumlist.size
    }
    class AlbumHolder(binding: AlbumItemBinding): RecyclerView.ViewHolder(binding.root) {
        val albumimage = binding.albumfileimage
        val albumname = binding.albumfilename
    }
}
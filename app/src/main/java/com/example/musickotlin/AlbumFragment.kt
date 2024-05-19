package com.example.musickotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AlbumFragment : Fragment() {

    var albums= MainActivity.albums
    private lateinit var recyclerview: RecyclerView
    private lateinit var albumAdapter: AlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_album, container, false)
        recyclerview=view.findViewById(R.id.albumrv)
        recyclerview.hasFixedSize()
        if (albums.isNotEmpty()){
            Toast.makeText(requireContext(),"not empty",Toast.LENGTH_SHORT).show()
            albumAdapter= AlbumAdapter(requireContext(),albums)
            recyclerview.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = albumAdapter
                setHasFixedSize(true)
            }
        }
        return view
    }

}
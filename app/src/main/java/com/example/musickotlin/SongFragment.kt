package com.example.musickotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SongFragment : Fragment() {
    var musicFiles= MainActivity.MusiclistMainactivity
    private lateinit var recyclerview:RecyclerView
    private lateinit var songadapter:SongAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_song, container, false)
        recyclerview = view.findViewById(R.id.songsrv)
        recyclerview.setHasFixedSize(true)
        recyclerview.setItemViewCacheSize(5)
        if (musicFiles.isNotEmpty()) {
            songadapter = SongAdapter(requireContext(), musicFiles)
            recyclerview.layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
            recyclerview.adapter = songadapter
        }
        return view
    }

}
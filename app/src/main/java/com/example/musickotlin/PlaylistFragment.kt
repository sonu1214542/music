package com.example.music

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musickotlin.R

class PlaylistsFragment : Fragment() {
    var recyclerview: RecyclerView? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview = view.findViewById<RecyclerView>(R.id.playlistrecycler)
        recyclerview!!.setHasFixedSize(true)
        playlistAdapter = PlaylistAdapter(requireContext(), PlaylistAdapter.playlists)
        recyclerview!!.setAdapter(playlistAdapter)
        recyclerview!!.setLayoutManager(LinearLayoutManager(context, RecyclerView.VERTICAL, false))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist, container, false)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var playlistAdapter: PlaylistAdapter? = null
    }
}
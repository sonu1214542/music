package com.example.musickotlin

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.music.PlaylistAdapter
import com.example.music.PlaylistsFragment

class PlayListDialog : DialogFragment() {
    private var editText: EditText? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add To Playlist")
        val inflater = requireActivity().layoutInflater
        val dialogView: View = inflater.inflate(R.layout.playlist_dialog, null)
        editText = dialogView.findViewById<EditText>(R.id.editText_playlist_name)
        builder.setView(dialogView)
        val playlistNamesSet: Set<String> =
            PlaylistAdapter.getPlaylists() // Get the set of playlist names
        val playlistNamesArray = playlistNamesSet.toTypedArray() // Convert set to array
        builder.setItems(
            playlistNamesArray
        ) { dialog, which ->
            val selectedPlaylistName = playlistNamesArray[which]
            val selectedplaylist: ArrayList<MusicClass> =
                PlaylistAdapter.playlists.get(selectedPlaylistName)!!
            if (!selectedplaylist.contains(PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos))) {
                selectedplaylist.add(PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos))
            }
            // Handle selection (e.g., add item to selected playlist)
            Toast.makeText(
                requireContext(),
                "Selected playlist: $selectedPlaylistName",
                Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }
        builder.setPositiveButton("Create New Playlist") { dialog, which ->
            val playlistName = editText!!.getText().toString().trim()
            if (!playlistName.isEmpty()) {
                var songToAdd: MusicClass? = null
                var newplaylist: ArrayList<MusicClass>? = null
                if (!PlaylistAdapter.playlists.containsKey(playlistName)) {
                    // Playlist does not exist, create a new one
                    newplaylist =
                        ArrayList<MusicClass>() // Initialize newplaylist with an empty ArrayList
                    PlaylistAdapter.playlists.put(
                        playlistName,
                        newplaylist
                    ) // Put the new playlist in the HashMap
                    songToAdd = PlayerActivity.playeractivityMusicList.get(PlayerActivity.songpos)
                    Toast.makeText(
                        context,
                        "playlists:" + PlaylistAdapter.playlists.keys,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Retrieve existing playlist
                    newplaylist = PlaylistAdapter.playlists.get(playlistName)
                }

                // Check if the song already exists in the playlist
                if (newplaylist != null && !newplaylist.contains(songToAdd)) {
                    if (songToAdd != null) {
                        newplaylist.add(songToAdd)
                    }
                    //Toast.makeText(getContext(), "Added " + songToAdd.getTitle() + " to " + playlistName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(
                        context,
                        "Song already exists in $playlistName",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter a playlist name", Toast.LENGTH_SHORT)
                    .show()
            }
            if (PlaylistsFragment.playlistAdapter != null) {
                Toast.makeText(context, "aaaaaaaaaaa", Toast.LENGTH_SHORT).show()
                PlaylistsFragment.playlistAdapter!!.notifyDataSetChanged()
            }
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which ->
            Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        // Create dialog
        return builder.create()
    }
}
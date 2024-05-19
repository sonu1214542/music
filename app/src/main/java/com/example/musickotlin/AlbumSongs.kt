package com.example.musickotlin

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musickotlin.databinding.ActivityAlbumSongsBinding

class AlbumSongs : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAlbumSongsBinding

    private lateinit var albumSongsAdapter: AlbumSongsAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var albumphoto: ImageView
    private lateinit var albumname:String
    companion object{
         lateinit var albumsongs: ArrayList<MusicClass>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumSongsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        albumsongs=ArrayList<MusicClass>()
        recyclerView = binding.albumsongsrv
        albumphoto=binding.albumsongsimg
        albumname= intent.getStringExtra("albumname").toString()
        var j = 0
        for (i in 0 until MainActivity.MusiclistMainactivity.size) {
            if (albumname == MainActivity.MusiclistMainactivity.get(i).album) {
                albumsongs.add(j, MainActivity.MusiclistMainactivity.get(i))
                j++
            }
        }
    }

    override fun onResume() {
        super.onResume()
            if (!(albumsongs.size<1)){
                albumSongsAdapter= AlbumSongsAdapter(this,albumsongs)
                recyclerView.adapter=albumSongsAdapter
                recyclerView.apply {
                    layoutManager=LinearLayoutManager(this@AlbumSongs,RecyclerView.VERTICAL,false,)
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_album_songs)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
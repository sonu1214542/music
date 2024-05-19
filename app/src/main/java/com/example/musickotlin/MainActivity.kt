package com.example.musickotlin

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaMetadataRetriever
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.music.PlaylistsFragment
import com.example.musickotlin.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import java.io.File
import java.io.IOException

data class Album(
    val name: String,
    val albumArt: ByteArray?
)


class MainActivity : AppCompatActivity() {

    lateinit var tab:TabLayout;
    lateinit var viewpager:ViewPager2;
    private lateinit var binder: ActivityMainBinding;
    private lateinit var fragmentadapter: FragmentPagerAdapter
    companion object{
        lateinit var MusiclistMainactivity:ArrayList<MusicClass>
        lateinit var albums: ArrayList<Album>
        fun getAlbumArt(uri: String?): ByteArray? {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(uri)
            val art = retriever.embeddedPicture
            try {
                retriever.release()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
            return art
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binder.root)
        requestRuntimePermission()
    }
    private fun addFragments(){
        fragmentadapter.addFragment(SongFragment())
        fragmentadapter.addFragment(AlbumFragment())
        fragmentadapter.addFragment(PlaylistsFragment())
    }

    private fun requestRuntimePermission(){
        if (Build.VERSION.SDK_INT>=33){
            if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_MEDIA_AUDIO)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_MEDIA_AUDIO),9)
                Toast.makeText(this,"permission not granted",Toast.LENGTH_SHORT).show()
            }else{
                MusiclistMainactivity=getAllAudio()
                initviews()
            }
        }else{
            if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),9)
                Toast.makeText(this,"permission not granted",Toast.LENGTH_SHORT).show()
            }else{
                MusiclistMainactivity=getAllAudio()
                initviews()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==9){
            if (grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show()
                MusiclistMainactivity=getAllAudio()
                initviews()
            }else{
                requestRuntimePermission()
            }
        }
    }
    @SuppressLint("Range")
    private fun getAllAudio():ArrayList<MusicClass>{
        val tempList = ArrayList<MusicClass>()
        val selection = MediaStore.Audio.Media.IS_MUSIC+"!=0"
        val projection = arrayOf(MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DATE_ADDED,MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.ALBUM)
        val cursor = this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null,null)
        val albumMap = mutableMapOf<String, ByteArray?>()
        if (cursor!=null){
            if (cursor.moveToFirst()){
                do {
                var cursortitle =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                var cursorid=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    var cursoralbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    var cursorartist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    var cursorduration=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    var cursorsongpath=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val fetchedmusic =MusicClass(id = cursorid, album = cursoralbum, title = cursortitle, duration = cursorduration, artist = cursorartist, path = cursorsongpath)
                    val file=File(fetchedmusic.path)
                    if (file.exists()){
                        tempList.add(fetchedmusic)
                    }
                    // Fetch album art if not already fetched
                    if (cursoralbum != null && !albumMap.containsKey(cursoralbum)) {
                        val albumArt = getAlbumArt(cursorsongpath)
                        albumMap[cursoralbum] = albumArt
                    }
                }while (cursor.moveToNext())
                cursor.close()
            }
        }
        albums = albumMap.map { Album(it.key, it.value) } as ArrayList<Album>
        return tempList
    }
    private fun initviews(){
        tab=binder.tablayout
        viewpager=binder.viewpager
        fragmentadapter= FragmentPagerAdapter(supportFragmentManager,lifecycle)
        addFragments()
        tab.addTab(tab.newTab().setText("Songs"))
        tab.addTab(tab.newTab().setText("Albums"))
        tab.addTab(tab.newTab().setText("Playlists"))
        viewpager.adapter=fragmentadapter
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            @SuppressLint("SuspiciousIndentation")
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!=null)
                    viewpager.currentItem=tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tab.selectTab(tab.getTabAt(position))  // Select the corresponding tab based on position
            }
        })
    }
}
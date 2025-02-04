package com.example.musickotlin

import java.util.concurrent.TimeUnit

data class MusicClass(val id:String, val title:String,val album:String,val path:String,val artist:String,val duration:Long)
fun formatsongDuration(duration:Long):String{
    val minutes = TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS)
    val seconds = (TimeUnit.SECONDS.convert(duration,TimeUnit.MILLISECONDS)-minutes*TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES))
    return String.format("%2d:%02d",minutes,seconds)
}
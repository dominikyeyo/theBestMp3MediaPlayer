package com.diegonunez.thebestmp3mediaplayer.domain.model

import android.graphics.Bitmap
import android.net.Uri

data class Mp3Metadata(
    val contentUri: Uri,
    val songId: Long,
    val cover: Bitmap?,
    val songTitle: String,
    val artist: String,
    val duration: Int
){
    companion object{
        fun emptyMetadata(): Mp3Metadata{
            return Mp3Metadata(
                contentUri = Uri.EMPTY,
                songId = 0L,
                cover = null,
                songTitle = "",
                artist = "",
                duration = 0
            )
        }
    }
}
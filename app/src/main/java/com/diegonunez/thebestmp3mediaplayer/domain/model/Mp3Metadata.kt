package com.diegonunez.thebestmp3mediaplayer.domain.model

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore

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
    fun getMusicUri() : Uri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, this.contentUri.path)
}

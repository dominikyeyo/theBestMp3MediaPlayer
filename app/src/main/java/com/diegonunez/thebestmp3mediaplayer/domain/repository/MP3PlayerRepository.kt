package com.diegonunez.thebestmp3mediaplayer.domain.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.diegonunez.thebestmp3mediaplayer.domain.model.Mp3Metadata
import kotlinx.coroutines.flow.Flow

interface MP3PlayerRepository {

    suspend fun loadCoverBitmap(context: Context, uri: Uri): Bitmap?

    suspend fun getAudios(): List<Mp3Metadata>

    suspend fun likeOrNotSong(id: Long)

    fun getLikedSongs(): Flow<List<Long>>

}
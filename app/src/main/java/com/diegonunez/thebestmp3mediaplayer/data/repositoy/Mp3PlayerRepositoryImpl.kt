package com.diegonunez.thebestmp3mediaplayer.data.repositoy

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.diegonunez.thebestmp3mediaplayer.domain.model.Mp3Metadata
import com.diegonunez.thebestmp3mediaplayer.domain.repository.MP3PlayerRepository
import com.diegonunez.thebestmp3mediaplayer.util.audio.MetadataHelper
import com.diegonunez.thebestmp3mediaplayer.util.audio.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Mp3PlayerRepositoryImpl @Inject constructor(
    private val metadataHelper: MetadataHelper,
    private val userPreferences: UserPreferences
) : MP3PlayerRepository {

    override suspend fun loadCoverBitmap(context: Context, uri: Uri): Bitmap? {
        return withContext(Dispatchers.IO) {
            metadataHelper.getAlbumArt(context = context, uri = uri)
        }
    }

    override suspend fun getAudios(): List<Mp3Metadata> {
        return withContext(Dispatchers.IO) {
            metadataHelper.getAudios()
        }
    }

    override suspend fun likeOrNotSong(id: Long) {
        withContext(Dispatchers.IO){
            userPreferences.likeOrNotSong(id = id)
        }
    }

    override fun getLikedSongs(): Flow<List<Long>> {
        return userPreferences.likedSongs
    }
}
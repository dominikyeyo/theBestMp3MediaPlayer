package com.diegonunez.thebestmp3mediaplayer.ui.activity

import android.content.Context
import com.diegonunez.thebestmp3mediaplayer.domain.model.Mp3Metadata


sealed class Mp3PlayerEvent{

    data class InitAudio(
        val audio: Mp3Metadata,
        val context: Context,
        val onAudioInitialized: () -> Unit
    ): Mp3PlayerEvent()

    data class Seek(val position: Float): Mp3PlayerEvent()

    data class LikeOrNotSong(val id: Long): Mp3PlayerEvent()

    object Play: Mp3PlayerEvent()

    object Pause: Mp3PlayerEvent()

    object Stop: Mp3PlayerEvent()

    object HideLoadingDialog: Mp3PlayerEvent()

    object LoadMedias: Mp3PlayerEvent()
}

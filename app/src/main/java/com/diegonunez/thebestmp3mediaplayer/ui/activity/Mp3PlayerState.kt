package com.diegonunez.thebestmp3mediaplayer.ui.activity

import com.diegonunez.thebestmp3mediaplayer.domain.model.Mp3Metadata


data class Mp3PlayerState(
    val isLoading: Boolean = false,
    val audios: List<Mp3Metadata> = emptyList(),
    val isPlaying: Boolean = false,
    val selectedAudio: Mp3Metadata = Mp3Metadata.emptyMetadata(),
    val currentPosition: Int = 0,
    val likedSongs: List<Long> = emptyList()
)
package com.diegonunez.thebestmp3mediaplayer.ui.activity

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegonunez.thebestmp3mediaplayer.domain.model.Mp3Metadata
import com.diegonunez.thebestmp3mediaplayer.domain.repository.MP3PlayerRepository
import com.diegonunez.thebestmp3mediaplayer.util.audio.VisualizerData
import com.diegonunez.thebestmp3mediaplayer.util.audio.VisualizerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MP3PlayerRepository
) : ViewModel() {

    private var _state by mutableStateOf(value = Mp3PlayerState())
    val state: Mp3PlayerState
        get() = _state

    private val _visualizerData =
        mutableStateOf(value = VisualizerData.emptyVisualizerData())
    val visualizerData: State<VisualizerData>
        get() = _visualizerData

    private var _player: MediaPlayer? = null

    private val _visualizerHelper = VisualizerHelper()

    private val _handler = Handler(Looper.getMainLooper())

    init {
        loadMedias()
    }


    fun onEvent(event: Mp3PlayerEvent) {
        when (event) {

            is Mp3PlayerEvent.InitAudio -> initAudio(
                audio = event.audio,
                context = event.context,
                onAudioInitialized = event.onAudioInitialized
            )

            is Mp3PlayerEvent.Seek -> seek(position = event.position)

            is Mp3PlayerEvent.LikeOrNotSong -> likeOrNotSong(id = event.id)

            Mp3PlayerEvent.Pause -> pause()

            Mp3PlayerEvent.Play -> play()


            Mp3PlayerEvent.Stop -> stop()

            Mp3PlayerEvent.HideLoadingDialog -> hideLoadingDialog()

            Mp3PlayerEvent.LoadMedias -> loadMedias()
        }
    }

    private fun initAudio(audio: Mp3Metadata, context: Context, onAudioInitialized: () -> Unit) {
        viewModelScope.launch {
            _state = _state.copy(isLoading = true)

            delay(800)

            val cover = repository.loadCoverBitmap(
                context = context,
                uri = audio.contentUri
            )

            _state = _state.copy(selectedAudio = audio.copy(cover = cover))

            _player = MediaPlayer().apply {
                setDataSource(context, audio.contentUri)
                prepare()
            }

            _player?.setOnCompletionListener {
                pause()
            }

            _player?.setOnPreparedListener {
                onAudioInitialized()
            }

            _state = _state.copy(isLoading = false)
        }
    }

    private fun play() {
        _state = _state.copy(isPlaying = true)

        _player?.start()

        _player?.run {
            _visualizerHelper.start(audioSessionId = audioSessionId, onData = { data ->
                _visualizerData.value = data
            })
        }
        _handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    _state = _state.copy(currentPosition = _player!!.currentPosition)
                    _handler.postDelayed(this, 1000)
                } catch (exp: Exception) {
                    _state = _state.copy(currentPosition = 0)
                }
            }

        }, 0)
    }

    private fun pause() {
        _state = _state.copy(isPlaying = false)
        _visualizerHelper.stop()
        _player?.pause()
    }

    private fun stop() {
        _visualizerHelper.stop()
        _player?.stop()
        _player?.reset()
        _player?.release()
        _state = _state.copy(
            isPlaying = false,
            currentPosition = 0
        )
        _player = null
    }

    private fun seek(position: Float) {
        _player?.run {
            seekTo(position.toInt())
        }
    }

    private fun loadMedias() {
        viewModelScope.launch {
            _state = _state.copy(isLoading = true)
            val audios = mutableStateListOf<Mp3Metadata>()
            audios.addAll(prepareAudios())
            _state = _state.copy(audios = audios)
            repository.getLikedSongs().collect { likedSongs ->
                _state = _state.copy(
                    likedSongs = likedSongs,
                    isLoading = false,
                )
            }
        }
    }

    private suspend fun prepareAudios(): List<Mp3Metadata> {
        return repository.getAudios().map {
            val artist = if (it.artist.contains("<unknown>"))
                "Unknown artist" else it.artist
            it.copy(artist = artist)
        }
    }

    private fun hideLoadingDialog() {
        _state = _state.copy(isLoading = false)
    }

    private fun likeOrNotSong(id: Long) {
        viewModelScope.launch {
            repository.likeOrNotSong(id = id)
        }
    }
}
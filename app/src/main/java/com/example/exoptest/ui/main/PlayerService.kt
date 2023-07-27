package com.example.exoptest.ui.main

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource

object PlayerService {

    private var player: Player? = null

    fun getPlayer(context: Context): Player {
        return player ?: createPlayer(context).apply { player = this }
    }

    private fun createPlayer(context: Context): Player {
        val player = ExoPlayer.Builder(context).build()

        // create a media item.
        val mediaItem = MediaItem.Builder()
            //.setUri("https://drive.google.com/uc?export=download&id=1Yv0IMtzVxGk5a1mr0_bunAvtIxF8MTxX") // problem
            .setUri("https://drive.google.com/uc?export=download&id=1SIK0wgyV0QLUy9NadiwPs_j_8ZUasJ1h")//4s
            //.setUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")//2s
            .setMimeType(MimeTypes.APPLICATION_MP4)
            .build()

        // Create a media source and pass the media item
        val mediaSource = ProgressiveMediaSource.Factory(
            DefaultDataSource.Factory(context)
        )
            .createMediaSource(mediaItem)

        // Finally assign this media source to the player
        return player.apply {
            setMediaSource(mediaSource)
            //playWhenReady = true // start playing when the exoplayer has setup
            seekTo(0, 0L) // Start from the beginning
            prepare() // Change the state from idle.
        }
    }
}
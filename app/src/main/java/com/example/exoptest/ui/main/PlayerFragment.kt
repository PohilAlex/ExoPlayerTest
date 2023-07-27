package com.example.exoptest.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.media3.common.Player
import androidx.media3.common.Player.Listener
import androidx.media3.ui.PlayerView
import com.example.exoptest.R
import kotlin.system.measureTimeMillis

/**
 * A simple [Fragment] subclass.
 * Use the [PlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayerFragment : Fragment() {

    private var player: Player? = null
    private var playerView: PlayerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerView = view.findViewById(R.id.playerView)
        playerView?.useController = false
        val prepareButton: Button = view.findViewById(R.id.prepareButton)
        prepareButton.setOnClickListener {
            val prepareStartTime = System.currentTimeMillis()
            val prepareTime = measureTimeMillis {
                player = PlayerService.getPlayer(requireContext())
            }
            Log.d("Test1","prepareTime=$prepareTime" )
            playerView?.player = player
            player?.play()
            player?.addListener(
                object : Listener {

                    override fun onPlaybackStateChanged(state: Int) {
                        super.onPlaybackStateChanged(state)
                        when (state) {
                            Player.STATE_IDLE -> Log.d("Test1", "STATE_IDLE")
                            Player.STATE_BUFFERING -> {
                                Log.d("Test1", "STATE_BUFFERING")
                            }
                            Player.STATE_READY -> {
                                Log.d("Test1", "STATE_READY")
                                Log.d("Test1", "firstFrameTime=" + (System.currentTimeMillis() - prepareStartTime))
                            }
                            Player.STATE_ENDED -> Log.d("Test1", "STATE_ENDED")
                        }
                    }
                }
            )
        }
    }

    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            playerView?.player = player
            player?.play()
        } else {
            player?.pause()
            playerView?.player = null
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() = PlayerFragment()
    }
}
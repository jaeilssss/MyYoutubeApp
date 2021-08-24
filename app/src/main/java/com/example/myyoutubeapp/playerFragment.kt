package com.example.myyoutubeapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myyoutubeapp.adapter.VideoAdapter
import com.example.myyoutubeapp.databinding.FragmentPlayerBinding
import com.example.myyoutubeapp.dto.VideoDto
import com.example.myyoutubeapp.service.VideoService
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.math.abs


class playerFragment : Fragment(R.layout.fragment_player) {


    private var binding : FragmentPlayerBinding? = null
    lateinit var videoAdapter : VideoAdapter
    private var player : SimpleExoPlayer ?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPlayerBinding = FragmentPlayerBinding.bind(view)

        binding = fragmentPlayerBinding
        videoAdapter = VideoAdapter(callback = {url , title ->
        play(url,title)
        })
        initMotionLayoutEvent(fragmentPlayerBinding)
        initRecyclerView(fragmentPlayerBinding)
        getVideoList()
        initControllButton(fragmentPlayerBinding)
        initplayer(fragmentPlayerBinding)

    }

    private fun initControllButton(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.bottomPlayerControllerButton.setOnClickListener {
            val player = this.player ?: return@setOnClickListener

            if(player.isPlaying){
                player.pause()
            }else{
                player.play()
            }


        }
    }


    fun play(url : String, title : String){
        binding?.let{
            context?.let{
                val dataSourceFactory = DefaultDataSourceFactory(it)

                val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
                player?.setMediaSource(mediaSource)
                player?.prepare() // prepare() 데이터를 가지오기 시작함
                player?.play()
            }
            it.playerMotionLayout.transitionToEnd()

            it.bottomTitleTextView.text = title

        }

    }
    fun initplayer(fragmentPlayerBinding: FragmentPlayerBinding){

         player = context?.let { SimpleExoPlayer.Builder(it).build() }
        fragmentPlayerBinding.playerView.player = player

        binding.let {
            player?.addListener(object : Player.EventListener{
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)

                    if(isPlaying){
                    it?.bottomPlayerControllerButton?.setImageResource(R.drawable.ic_baseline_pause_24)
                    }else{
                        it?.bottomPlayerControllerButton?.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    }
                }
            })
        }



    }
    fun initRecyclerView(fragmentPlayerBinding: FragmentPlayerBinding){
        fragmentPlayerBinding.fragmentRecyclerView.apply {
                adapter = videoAdapter
                layoutManager = LinearLayoutManager(context)
        }

    }
    private fun initMotionLayoutEvent(fragmentPlayerBinding : FragmentPlayerBinding){
        fragmentPlayerBinding.playerMotionLayout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
            ) {

            }

            override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
            ) {
                binding?.let{

                    (activity as MainActivity).also { mainActivity ->
                        mainActivity.findViewById<MotionLayout>(R.id.mainMotionLayout).progress= abs(progress)
                    }
                }
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {

            }

            override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
            ) {

            }

        })
    }

    private fun getVideoList(){
        val retrofit = Retrofit.Builder()
                .baseUrl("https://run.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        retrofit.create(VideoService::class.java).also { it ->

            it.listVideos()
                    .enqueue(object : Callback<VideoDto> {
                        override fun onResponse(call: Call<VideoDto>, response: Response<VideoDto>) {

                            if(response.isSuccessful.not()){
                                Log.d("MainActivity","response not successful")
                                return
                            }
                            response.body()?.let {
                                Log.d("MainActivity",it.toString())

                                videoAdapter.submitList(it.videos)
                            }
                        }

                        override fun onFailure(call: Call<VideoDto>, t: Throwable) {
                            Log.e("MainActivity","response fail")
                        }

                    })
        }


    }

    override fun onStop() {
        player?.pause()
        super.onStop()
    }
    override fun onDestroy() {
        binding = null
        player?.release()
        super.onDestroy()
    }
}
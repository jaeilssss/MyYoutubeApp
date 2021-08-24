package com.example.myyoutubeapp

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myyoutubeapp.adapter.VideoAdapter
import com.example.myyoutubeapp.dto.VideoDto
import com.example.myyoutubeapp.service.VideoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var videoAdapter : VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, playerFragment())
            .commit()

        videoAdapter = VideoAdapter(callback = { url ,title ->
            supportFragmentManager.fragments.find { it is playerFragment }?.let {
                (it as playerFragment).play(url,title)


            }
        })

        findViewById<RecyclerView>(R.id.Rcv).apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
        }
        getVideoList()
        makeStatusBarTransparent()
    }



    private fun getVideoList(){
        val retrofit = Retrofit.Builder()
                .baseUrl("https://run.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        retrofit.create(VideoService::class.java).also { it ->

            it.listVideos()
                    .enqueue(object : Callback<VideoDto>{
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

    fun Activity.makeStatusBarTransparent() {
        with(window) {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }
    }

}
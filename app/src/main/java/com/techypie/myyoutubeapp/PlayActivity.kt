package com.techypie.myyoutubeapp

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.techypie.myyoutubeapp.databinding.ActivityPlayBinding

class PlayActivity : AppCompatActivity() {

    lateinit var binding : ActivityPlayBinding
    lateinit var video : Video
    lateinit var videoList : MutableList<Video>
    lateinit var databasaHelper: DatabasaHelper
    lateinit var adapterVideo: AdapterVideoCategory
    var isFullScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
enableEdgeToEdge()
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        video = intent.getSerializableExtra("Video") as Video
        var videoId = video.videoid

        databasaHelper = DatabasaHelper(this)

        videoList = ArrayList()

        val iFramePlayerOptions = IFramePlayerOptions.Builder(applicationContext)
            .controls(1)
            .fullscreen(1) // enable full screen button
            .build()

        binding.youtubePlayer.enableAutomaticInitialization = false

        binding.videoTitleTV.text = video.title

        binding.youtubePlayer.addFullscreenListener(object : FullscreenListener{
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                isFullScreen = true

                binding.youtubePlayer.visibility = View.GONE
                binding.fullScreenVideo.visibility = View.VISIBLE
                binding.fullScreenVideo.addView(fullscreenView)
                binding.box.visibility = View.GONE
               requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

            }

            override fun onExitFullscreen() {
                isFullScreen = false

                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    // the video will continue playing in the player
                binding.youtubePlayer.visibility = View.VISIBLE
                binding.fullScreenVideo.visibility = View.GONE
                binding.fullScreenVideo.removeAllViews()
            }

        })


        binding.youtubePlayer.initialize(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.cueVideo(videoId,0f)
            }
        },iFramePlayerOptions)
        lifecycle.addObserver(binding.youtubePlayer)
        setupCategoryRecycler()

    }

    fun setupCategoryRecycler() {
        binding.videoCategoryTV.text = "${video.category} Related Video"
        videoList.clear()

        if (video.category == "Education"){
            videoList = databasaHelper.getEducationVideo()
        }
        else if(video.category == "Entertainment"){
            videoList = databasaHelper.getEntertainmentVideo()
        }
        else if(video.category == "Gaming"){
            videoList = databasaHelper.getGamingVideo()
        }
        else if(video.category == "Sports"){
            videoList = databasaHelper.getSportsVideo()
        }
        else{
            videoList = databasaHelper.getNewsVideo()
        }

        adapterVideo = AdapterVideoCategory(this,videoList)
        binding.reyclcerCategory.adapter = adapterVideo
        binding.reyclcerCategory.layoutManager = LinearLayoutManager(this@PlayActivity)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayer.release()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.box.visibility = View.GONE
        }
        else {
            binding.box.visibility = View.VISIBLE
        }
    }
}
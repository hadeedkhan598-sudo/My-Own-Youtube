package com.techypie.myyoutubeapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.techypie.myyoutubeapp.databinding.ActivityPlayBinding

class PlayActivity : AppCompatActivity() {

    lateinit var binding : ActivityPlayBinding
    lateinit var video : Video
    lateinit var videoList : MutableList<Video>
    lateinit var databasaHelper: DatabasaHelper
    lateinit var adapterVideo: AdapterVideoCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        video = intent.getSerializableExtra("Video") as Video
        var videoId = video.videoid

        databasaHelper = DatabasaHelper(this)

        videoList = ArrayList()


        binding.videoTitleTV.text = video.title
        binding.youtubePlayer.addYouTubePlayerListener(object : YouTubePlayerListener{
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoId,0f)
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {

            }

            override fun onPlaybackQualityChange(
                youTubePlayer: YouTubePlayer,
                playbackQuality: PlayerConstants.PlaybackQuality
            ) {

            }

            override fun onPlaybackRateChange(
                youTubePlayer: YouTubePlayer,
                playbackRate: PlayerConstants.PlaybackRate
            ) {

            }

            override fun onError(
                youTubePlayer: YouTubePlayer,
                error: PlayerConstants.PlayerError
            ) {

            }

            override fun onCurrentSecond(
                youTubePlayer: YouTubePlayer,
                second: Float
            ) {

            }

            override fun onVideoDuration(
                youTubePlayer: YouTubePlayer,
                duration: Float
            ) {

            }

            override fun onVideoLoadedFraction(
                youTubePlayer: YouTubePlayer,
                loadedFraction: Float
            ) {

            }

            override fun onVideoId(
                youTubePlayer: YouTubePlayer,
                videoId: String
            ) {

            }

            override fun onApiChange(youTubePlayer: YouTubePlayer) {

            }

        })

        setupCategoryRecycler()

    }

    fun setupCategoryRecycler()
    {
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
}
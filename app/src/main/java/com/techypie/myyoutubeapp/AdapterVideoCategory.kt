package com.techypie.myyoutubeapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AdapterVideoCategory(context: Context, videoList: MutableList<Video>) : RecyclerView.Adapter<AdapterVideoCategory.ViewHolder>() {

    var context : Context
    var videoList : MutableList<Video>
    lateinit var databasaHelper: DatabasaHelper


    init {
        this.context = context
        this.videoList = videoList
        databasaHelper = DatabasaHelper(context)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_video_category,p0,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {

        var position = holder.bindingAdapterPosition
        holder.id.text = "#${videoList[position].id}"
        holder.title.text = videoList[position].title

        val videoPath = "https://img.youtube.com/vi/" + videoList[position].videoid + "/0.jpg"
        Glide.with(context).load(videoPath).placeholder(R.drawable.bg_placeholder).centerCrop().error(R.drawable.bg_placeholder).into(holder.videoUrl)


        holder.videoUrl.setOnClickListener {
            context.startActivity(Intent(context, PlayActivity::class.java)
                .putExtra("Video", videoList[position]))
        }

    }


    override fun getItemCount(): Int {
       return videoList.size
    }



    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var title = itemView.findViewById<TextView>(R.id.videoTitleTV)
        var id = itemView.findViewById<TextView>(R.id.videoIdTV)
        var videoUrl = itemView.findViewById<ImageView>(R.id.videoUrl)



    }



}
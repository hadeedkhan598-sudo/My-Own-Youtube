package com.techypie.myyoutubeapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabasaHelper(context : Context?) : SQLiteOpenHelper(context,"VIDEO",null,1) {

    init {
        writableDatabase.execSQL("CREATE TABLE IF NOT EXISTS VIDEO(ID INTEGER PRIMARY KEY AUTOINCREMENT , TITLE TEXT , VIDEO_ID TEXT , CATEGORY TEXT)")
    }


    fun insertVideo(title : String , videoId : String , category : String) : String
    {
        var values = ContentValues().apply {
            put("TITLE",title)
            put("VIDEO_ID",videoId)
            put("CATEGORY",category)
        }

        var isVideoAdded = writableDatabase.insert("VIDEO",null,values)
        if (isVideoAdded == -1L)
            return "Error"
        else
            return "Added Successfully"
    }


    fun getAllVideo() : MutableList<Video>
    {
        var cursor = writableDatabase.rawQuery("SELECT * FROM VIDEO",null)
        var videoList = mutableListOf<Video>()
        while (cursor.moveToNext()){
            var id = cursor.getString(0)
            var title = cursor.getString(1)
            var videoId = cursor.getString(2)
            var category = cursor.getString(3)

            var singleVideo = Video(id,title,videoId,category)
            videoList.add(singleVideo)
        }

        return videoList
    }

    fun getEducationVideo() : MutableList<Video>
    {
        var cursor = writableDatabase.rawQuery("SELECT * FROM VIDEO WHERE CATEGORY = 'Education'",null)
        var videoList = mutableListOf<Video>()
        while (cursor.moveToNext()){
            var id = cursor.getString(0)
            var title = cursor.getString(1)
            var videoId = cursor.getString(2)
            var category = cursor.getString(3)

            var singleVideo = Video(id,title,videoId,category)
            videoList.add(singleVideo)
        }

        return videoList
    }

    fun getEntertainmentVideo() : MutableList<Video>
    {
        var cursor = writableDatabase.rawQuery("SELECT * FROM VIDEO WHERE CATEGORY = 'Entertainment'",null)
        var videoList = mutableListOf<Video>()
        while (cursor.moveToNext()){
            var id = cursor.getString(0)
            var title = cursor.getString(1)
            var videoId = cursor.getString(2)
            var category = cursor.getString(3)

            var singleVideo = Video(id,title,videoId,category)
            videoList.add(singleVideo)
        }

        return videoList
    }
    fun getGamingVideo() : MutableList<Video>
    {
        var cursor = writableDatabase.rawQuery("SELECT * FROM VIDEO WHERE CATEGORY = 'Gaming'",null)
        var videoList = mutableListOf<Video>()
        while (cursor.moveToNext()){
            var id = cursor.getString(0)
            var title = cursor.getString(1)
            var videoId = cursor.getString(2)
            var category = cursor.getString(3)

            var singleVideo = Video(id,title,videoId,category)
            videoList.add(singleVideo)
        }

        return videoList
    }
    fun getSportsVideo() : MutableList<Video>
    {
        var cursor = writableDatabase.rawQuery("SELECT * FROM VIDEO WHERE CATEGORY = 'Sports'",null)
        var videoList = mutableListOf<Video>()
        while (cursor.moveToNext()){
            var id = cursor.getString(0)
            var title = cursor.getString(1)
            var videoId = cursor.getString(2)
            var category = cursor.getString(3)

            var singleVideo = Video(id,title,videoId,category)
            videoList.add(singleVideo)
        }

        return videoList
    }
    fun getNewsVideo() : MutableList<Video>
    {
        var cursor = writableDatabase.rawQuery("SELECT * FROM VIDEO WHERE CATEGORY = 'News'",null)
        var videoList = mutableListOf<Video>()
        while (cursor.moveToNext()){
            var id = cursor.getString(0)
            var title = cursor.getString(1)
            var videoId = cursor.getString(2)
            var category = cursor.getString(3)

            var singleVideo = Video(id,title,videoId,category)
            videoList.add(singleVideo)
        }

        return videoList
    }

    // Delete Video

    fun deleteVideo(id : String) : String
    {

        var noOfDeletedRecords = writableDatabase.delete("VIDEO","ID=?",arrayOf(id))
        if (noOfDeletedRecords>0)
            return "Deleted"
        else
            return "Error"
    }

    override fun onCreate(p0: SQLiteDatabase?) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}
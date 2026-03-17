package com.techypie.myyoutubeapp

import java.io.Serializable

data class Video(
    var id: String = "",
    var title: String = "",
    var videoid: String = "",
    var category: String = "",
) : Serializable

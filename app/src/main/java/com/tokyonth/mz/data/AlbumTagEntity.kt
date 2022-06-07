package com.tokyonth.mz.data

import com.google.gson.annotations.SerializedName

data class AlbumTagEntity(

    @field:SerializedName("addtime")
    val addtime: String,

    @field:SerializedName("num")
    val num: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("pic")
    val pic: String,

    @field:SerializedName("text")
    val text: String

)

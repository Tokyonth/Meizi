package com.tokyonth.mz.data

import com.google.gson.annotations.SerializedName

open class AlbumPictureEntity(

    @field:SerializedName("jigou_name")
    val jigouName: String,

    @field:SerializedName("jigou_pic")
    val jigouPic: String,

    @field:SerializedName("piclist")
    val piclist: List<String>,

    @field:SerializedName("motel_id")
    val motelId: String,

    @field:SerializedName("pic")
    val pic: String,

    @field:SerializedName("jigou_id")
    val jigouId: String,

    @field:SerializedName("motel_pic")
    val motelPic: String,

    @field:SerializedName("addtime")
    val addtime: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("motel_name")
    val motelName: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("text")
    val text: String,

    @field:SerializedName("nums")
    val nums: Int

)

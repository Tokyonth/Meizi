package com.tokyonth.mz.data

import com.google.gson.annotations.SerializedName

data class MovieEntity(

    @field:SerializedName("vod_pic")
    val vodPic: String? = null,

    @field:SerializedName("vod_content")
    val vodContent: String? = null,

    @field:SerializedName("vod_year")
    val vodYear: Any? = null,

    @field:SerializedName("vpath")
    val vpath: String? = null,

    @field:SerializedName("vod_id")
    val vodId: String? = null,

    @field:SerializedName("vod_title")
    val vodTitle: String? = null,

    @field:SerializedName("vod_area")
    val vodArea: Any? = null,

    @field:SerializedName("vod_director")
    val vodDirector: String? = null,

    @field:SerializedName("state")
    val state: String? = null,

    @field:SerializedName("vod_addtime")
    val vodAddtime: String? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("vod_cid")
    val vodCid: Int? = null,

    @field:SerializedName("playfrom")
    val playfrom: String? = null,

    @field:SerializedName("vod_language")
    val vodLanguage: Any? = null,

    @field:SerializedName("vod_actor")
    val vodActor: String? = null

)

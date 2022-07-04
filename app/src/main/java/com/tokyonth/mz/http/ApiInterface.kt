package com.tokyonth.mz.http

import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.data.AlbumTagEntity
import com.tokyonth.mz.data.DetailAlbumEntity
import com.tokyonth.mz.data.MovieEntity
import com.tokyonth.mz.http.url.ReplaceBaseUrl

import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("/api/lists/reco")
    suspend fun getChosenAlbum(
        @FieldMap map: Map<String, String>
    ): BaseResponse<List<AlbumPictureEntity>>

    @FormUrlEncoded
    @POST("/api/lists/addtime")
    suspend fun getLatestAlbum(
        @FieldMap map: Map<String, String>
    ): BaseResponse<List<AlbumPictureEntity>>

    @FormUrlEncoded
    @POST("/api/lists/hits")
    suspend fun getHottestAlbum(
        @FieldMap map: Map<String, String>
    ): BaseResponse<List<AlbumPictureEntity>>

    @FormUrlEncoded
    @POST("/api/lists/show")
    suspend fun getDetailAlbum(
        @FieldMap map: Map<String, String>
    ): DetailAlbumEntity

    @FormUrlEncoded
    @POST("/api/lists/search")
    suspend fun getSearchAlbum(
        @FieldMap map: Map<String, String>
    ): BaseResponse<List<AlbumPictureEntity>>

    @FormUrlEncoded
    @POST("/api/lists/jigou_type")
    suspend fun getTeamAlbum(
        @FieldMap map: Map<String, String>
    ): BaseResponse<List<AlbumPictureEntity>>

    @FormUrlEncoded
    @POST("/api/lists/motel_type")
    suspend fun getMotelAlbum(
        @FieldMap map: Map<String, String>
    ): BaseResponse<List<AlbumPictureEntity>>

    @FormUrlEncoded
    @POST("/api/lists/tags_type")
    suspend fun getTagAlbum(
        @FieldMap map: Map<String, String>
    ): BaseResponse<List<AlbumPictureEntity>>

    @FormUrlEncoded
    @POST("/api/lists/tags")
    suspend fun getCategoryTag(
        @FieldMap map: Map<String, String>
    ): BaseResponse<List<AlbumTagEntity>>

    @FormUrlEncoded
    @POST("/api/lists/jigou")
    suspend fun getTeamTag(
        @FieldMap map: Map<String, String>
    ): BaseResponse<List<AlbumTagEntity>>

    @FormUrlEncoded
    @POST("/api/lists/motel")
    suspend fun getMotelTag(
        @FieldMap map: Map<String, String>
    ): BaseResponse<List<AlbumTagEntity>>

    @POST("/api/lists/resou")
    suspend fun getHotTag(): BaseResponse<List<String>>

    @ReplaceBaseUrl("http://f2dcj6.com")
    @POST("/sapi/json")
    suspend fun getFedVideo(
        @Query("ac") type: String,
        @Query("pg") page: String
    ): BaseResponse<List<MovieEntity>>

}

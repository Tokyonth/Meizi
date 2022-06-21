package com.tokyonth.mz.viewmodel

import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.http.ApiRepository
import com.tokyonth.mz.http.BaseResponse

class AlbumPictureViewModel : BaseListViewModel<AlbumPictureEntity>() {

    companion object {

        const val CHOSEN = 0
        const val LATEST = 1
        const val HOTTEST = 2
        const val SEARCH = 3

    }

    private var albumType = 0

    fun setAlbumType(id: Int) {
        this.albumType = id
    }

    override suspend fun setListApi(map: HashMap<String, String>): BaseResponse<List<AlbumPictureEntity>>? {
        return when (albumType) {
            CHOSEN -> ApiRepository.api.getChosenAlbum(map)
            LATEST -> ApiRepository.api.getLatestAlbum(map)
            HOTTEST -> ApiRepository.api.getHottestAlbum(map)
            SEARCH -> ApiRepository.api.getSearchAlbum(map)
            else -> null
        }
    }

}

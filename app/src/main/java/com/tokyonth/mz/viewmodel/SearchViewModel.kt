package com.tokyonth.mz.viewmodel

import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.http.ApiRepository
import com.tokyonth.mz.http.BaseResponse
import com.tokyonth.mz.ui.fragment.search.SearchType

class SearchViewModel : AlbumPictureViewModel() {

    private var searchType: SearchType? = null

    fun getSearchAlbumData(searchType: SearchType) {
        this.searchType = searchType
        getAlbumData()
    }

    override suspend fun setAlbumApi(map: HashMap<String, String>): BaseResponse<List<AlbumPictureEntity>> {
        return when (searchType) {
            SearchType.TEXT -> {
                ApiRepository.api.getSearchAlbum(map)
            }
            SearchType.TEAM -> {
                ApiRepository.api.getTeamAlbum(map)
            }
            SearchType.MOTEL -> {
                ApiRepository.api.getMotelAlbum(map)
            }
            else -> {
                ApiRepository.api.getSearchAlbum(map)
            }
        }
    }

}

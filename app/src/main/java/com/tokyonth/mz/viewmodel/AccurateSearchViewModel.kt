package com.tokyonth.mz.viewmodel

import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.http.ApiRepository
import com.tokyonth.mz.http.BaseResponse
import com.tokyonth.mz.ui.fragment.search.SearchType

class AccurateSearchViewModel : BaseListViewModel<AlbumPictureEntity>() {

    private var searchType: SearchType? = null

    fun setAccurateSearchType(searchType: SearchType) {
        this.searchType = searchType
    }

    override suspend fun setListApi(map: HashMap<String, String>): BaseResponse<List<AlbumPictureEntity>>? {
        return when (searchType) {
            SearchType.CATEGORY -> ApiRepository.api.getTagAlbum(map)
            SearchType.TEAM -> ApiRepository.api.getTeamAlbum(map)
            SearchType.MOTEL -> ApiRepository.api.getMotelAlbum(map)
            else -> null
        }
    }

}

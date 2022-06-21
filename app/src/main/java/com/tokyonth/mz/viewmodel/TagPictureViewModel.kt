package com.tokyonth.mz.viewmodel

import com.tokyonth.mz.Constants
import com.tokyonth.mz.data.AlbumTagEntity
import com.tokyonth.mz.http.ApiRepository
import com.tokyonth.mz.http.BaseResponse
import com.tokyonth.mz.ui.fragment.search.SearchType

class TagPictureViewModel : BaseListViewModel<AlbumTagEntity>() {

    private var searchType: SearchType? = null

    fun setTagPictureType(tagType: SearchType) {
        this.searchType = tagType
    }

    override suspend fun setListApi(map: HashMap<String, String>): BaseResponse<List<AlbumTagEntity>>? {
        map.apply {
            put(Constants.API_PAGE_SIZE_MAP_KEY, Constants.API_DEFAULT_PAGE_SIZE)
        }
        return when (searchType) {
            SearchType.CATEGORY -> ApiRepository.api.getCategoryTag(map)
            SearchType.MOTEL -> ApiRepository.api.getMotelTag(map)
            SearchType.TEAM -> ApiRepository.api.getTeamTag(map)
            else -> null
        }
    }

}

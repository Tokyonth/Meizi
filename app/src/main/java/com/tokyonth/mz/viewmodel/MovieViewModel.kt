package com.tokyonth.mz.viewmodel

import com.tokyonth.mz.Constants
import com.tokyonth.mz.data.MovieEntity
import com.tokyonth.mz.http.ApiRepository
import com.tokyonth.mz.http.BaseResponse

class MovieViewModel : BaseListViewModel<MovieEntity>() {

    override suspend fun setListApi(map: HashMap<String, String>): BaseResponse<List<MovieEntity>> {
        val page = map[Constants.API_PAGE_MAP_KEY]!!
        return ApiRepository.api.getFedVideo("videolist", page)
    }

}

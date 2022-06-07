package com.tokyonth.mz.viewmodel

import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.http.ApiRepository
import com.tokyonth.mz.http.BaseResponse

class HottestViewModel : AlbumPictureViewModel() {

    override suspend fun setAlbumApi(map: HashMap<String, String>): BaseResponse<List<AlbumPictureEntity>> {
        return ApiRepository.api.getHottestAlbum(map)
    }

}

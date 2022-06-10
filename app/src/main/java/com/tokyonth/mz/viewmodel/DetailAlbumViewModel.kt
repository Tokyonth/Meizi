package com.tokyonth.mz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.tokyonth.mz.Constants
import com.tokyonth.mz.data.DetailAlbumEntity
import com.tokyonth.mz.http.ApiRepository
import com.tokyonth.mz.http.requestResult
import com.tokyonth.mz.utils.ktx.toast

class DetailAlbumViewModel : ViewModel() {

    val detailAlbumLiveData = MutableLiveData<DetailAlbumEntity>()

    fun getDetailAlbum(id: String) {
        requestResult({
            val map = mapOf(Pair(Constants.API_ALBUM_ID_MAP_KEY, id))
            ApiRepository.api.getDetailAlbum(map)
        }, {
            detailAlbumLiveData.value = it
        }, {
            toast(it.errorMsg)
        })
    }

}

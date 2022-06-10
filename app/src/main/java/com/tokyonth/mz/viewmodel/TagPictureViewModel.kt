package com.tokyonth.mz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.tokyonth.mz.Constants
import com.tokyonth.mz.data.AlbumTagEntity
import com.tokyonth.mz.http.ApiRepository
import com.tokyonth.mz.http.BaseResponse
import com.tokyonth.mz.http.requestResult

class TagPictureViewModel : ViewModel() {

    val pictureLiveData = MutableLiveData<MutableList<AlbumTagEntity>>()

    val loadMoreLiveData = MutableLiveData<Boolean>()

    val refreshLiveData = MutableLiveData<Boolean>()

    val errorLiveData = MutableLiveData<String>()

    private var formData = HashMap<String, String>()

    private var pageIndex = 1

    private var tagType = -1

    private var isLoadMore = false

    private var isRefresh = false

    fun setTagPictureType(tagType: Int) {
        this.tagType = tagType
    }

    private suspend fun pictureApiType(type: Int): BaseResponse<List<AlbumTagEntity>>? {
        formData.apply {
            put(Constants.API_PAGE_MAP_KEY, pageIndex.toString())
            put(Constants.API_PAGE_SIZE_MAP_KEY, Constants.API_DEFAULT_PAGE_SIZE)
        }
        return when (type) {
            0 -> {
                ApiRepository.api.getCategoryTag(formData)
            }
            1 -> {
                ApiRepository.api.getMotelTag(formData)
            }
            2 -> {
                ApiRepository.api.getTeamTag(formData)
            }
            else -> {
                null
            }
        }
    }

    private fun getTagPictureData() {
        requestResult({
            pictureApiType(tagType)!!
        }, {
            if (isLoadMore) {
                loadMoreLiveData.value = true
                isLoadMore = false
            }
            if (isRefresh) {
                refreshLiveData.value = true
                isRefresh = false
            }
            pictureLiveData.value = it.data?.toMutableList()
        }, {
            if (pageIndex >= 1) {
                pageIndex -= 1
            }
            if (isLoadMore) {
                loadMoreLiveData.value = false
                isLoadMore = false
            }
            if (isRefresh) {
                refreshLiveData.value = false
                isRefresh = false
            }
            errorLiveData.value = it.errorMsg
            //toast(it.errorMsg)
        })
    }

    fun nextPage() {
        pageIndex += 1
        isLoadMore = true
        getTagPictureData()
    }

    fun refreshPage() {
        pageIndex = 1
        isRefresh = true
        getTagPictureData()
    }

}

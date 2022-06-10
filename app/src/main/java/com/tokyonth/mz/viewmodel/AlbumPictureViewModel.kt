package com.tokyonth.mz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.tokyonth.mz.Constants
import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.http.BaseResponse
import com.tokyonth.mz.http.requestResult

abstract class AlbumPictureViewModel : ViewModel() {

    val albumLiveData = MutableLiveData<MutableList<AlbumPictureEntity>>()

    val loadMoreLiveData = MutableLiveData<Boolean>()

    val refreshLiveData = MutableLiveData<Boolean>()

    val errorLiveData = MutableLiveData<String>()

    private var formData = HashMap<String, String>()

    private var pageIndex = 1

    private var isLoadMore = false

    private var isRefresh = false

    abstract suspend fun setAlbumApi(map: HashMap<String, String>):
            BaseResponse<List<AlbumPictureEntity>>

    fun addParameterData(vararg p: Pair<String, String>) {
        p.forEach {
            formData[it.first] = it.second
        }
    }

    private fun getAlbumData() {
        requestResult({
            formData[Constants.API_PAGE_MAP_KEY] = pageIndex.toString()
            setAlbumApi(formData)
        }, {
            if (isLoadMore) {
                loadMoreLiveData.value = true
                isLoadMore = false
            }
            if (isRefresh) {
                refreshLiveData.value = true
                isRefresh = false
            }
            albumLiveData.value = it.data?.toMutableList()
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
        getAlbumData()
    }

    fun refreshPage() {
        pageIndex = 1
        isRefresh = true
        getAlbumData()
    }

}

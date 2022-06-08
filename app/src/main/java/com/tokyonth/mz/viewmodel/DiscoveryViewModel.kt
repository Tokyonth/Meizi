package com.tokyonth.mz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tokyonth.mz.Constants
import com.tokyonth.mz.data.AlbumTagEntity
import com.tokyonth.mz.http.ApiRepository
import com.tokyonth.mz.http.requestResult

class DiscoveryViewModel : ViewModel() {

    val categoryTagViewModel = MutableLiveData<List<AlbumTagEntity>>()

    val teamTagViewModel = MutableLiveData<List<AlbumTagEntity>>()

    val motelTagViewModel = MutableLiveData<List<AlbumTagEntity>>()

    fun getCategoryTag() {
        requestResult({
            ApiRepository.api.getCategoryTag(buildMap())
        }, {
            categoryTagViewModel.value = it.data
        }, {

        })
    }

    fun getTeamTag() {
        requestResult({
            ApiRepository.api.getTeamTag(buildMap())
        }, {
            teamTagViewModel.value = it.data
        }, {

        })
    }

    fun getMotelTag() {
        requestResult({
            ApiRepository.api.getMotelTag(buildMap())
        }, {
            motelTagViewModel.value = it.data
        }, {

        })
    }

    private fun buildMap(): HashMap<String, String> {
        return HashMap<String, String>().apply {
            put(Constants.API_PAGE_MAP_KEY, "1")
            put(Constants.API_PAGE_SIZE_MAP_KEY, "30")
        }
    }

}

package com.tokyonth.mz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tokyonth.mz.data.AlbumTagEntity
import com.tokyonth.mz.http.ApiRepository
import com.tokyonth.mz.http.requestResult

class DiscoveryViewModel : ViewModel() {

    val categoryTagViewModel = MutableLiveData<List<AlbumTagEntity>>()

    val teamTagViewModel = MutableLiveData<List<AlbumTagEntity>>()

    val motelTagViewModel = MutableLiveData<List<AlbumTagEntity>>()

    fun getCategoryTag() {
        requestResult({
            ApiRepository.api.getCategoryTag()
        }, {
            categoryTagViewModel.value = it.data
        }, {

        })
    }

    fun getTeamTag() {
        requestResult({
            ApiRepository.api.getTeamTag()
        }, {
            teamTagViewModel.value = it.data
        }, {

        })
    }

    fun getMotelTag() {
        requestResult({
            ApiRepository.api.getMotelTag()
        }, {
            motelTagViewModel.value = it.data
        }, {

        })
    }

}

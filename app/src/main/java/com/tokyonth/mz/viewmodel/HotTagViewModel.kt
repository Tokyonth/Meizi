package com.tokyonth.mz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.tokyonth.mz.http.ApiRepository
import com.tokyonth.mz.http.requestResult

class HotTagViewModel : ViewModel() {

    val hotTagViewModel = MutableLiveData<List<String>>()

    fun getHotTag() {
        requestResult({
            ApiRepository.api.getHotTag()
        }, {
            hotTagViewModel.value = it.data!!
            hotTagViewModel.value = emptyList()
        })
    }

}

package com.tokyonth.mz.viewmodel

import androidx.lifecycle.ViewModel
import com.tokyonth.mz.http.ApiRepository
import com.tokyonth.mz.http.requestResult
import com.tokyonth.mz.utils.UnPeekLiveData

class HotTagViewModel : ViewModel() {

    val hotTagViewModel = UnPeekLiveData<List<String>>()

    fun getHotTag() {
        requestResult({
            ApiRepository.api.getHotTag()
        }, {
            hotTagViewModel.setValue(it.data!!)
        })
    }

}

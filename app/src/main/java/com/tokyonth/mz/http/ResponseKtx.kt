package com.tokyonth.mz.http

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.tokyonth.mz.http.error.ApiError
import com.tokyonth.mz.http.error.ApiException

import kotlinx.coroutines.launch

fun <T : BaseResponse<*>> ViewModel.requestResult(
    apiBlock: suspend () -> T,
    onSuccess: (T) -> Unit,
    onError: ((ApiException) -> Unit)? = null,
    onFinished: (() -> Unit)? = null
) {
    viewModelScope.launch {
        runCatching {
            apiBlock.invoke()
        }.onSuccess {
            if (it.isSuccess()) {
                if (it.data != null) {
                    onSuccess.invoke(it)
                } else {
                    onError?.invoke(ApiException(ApiError.NOT_MORE_DATA))
                }
            } else {
                onError?.invoke(ApiException(ApiError.UNKNOWN))
            }
            onFinished?.invoke()
        }.onFailure {
            val error = if (it is ApiException) {
                it
            } else {
                ApiException("未知错误: ${it.message}", it)
            }
            onError?.invoke(error)
            onFinished?.invoke()
        }
    }
}

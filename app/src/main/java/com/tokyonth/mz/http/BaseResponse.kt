package com.tokyonth.mz.http

open class BaseResponse<T> {

    open val code: Int = 0

    open val data: T? = null

    fun isSuccess(): Boolean {
        return code == 200 || code == 1
    }

}

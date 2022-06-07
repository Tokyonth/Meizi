package com.tokyonth.mz.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AppendParamInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest
            .newBuilder()
            .addHeader("content-type", "application/x-www-form-urlencoded")
            .addHeader("accept-encoding", "gzip")
            .url(originalRequest.url)
            .build()
        return chain.proceed(request)
    }

}

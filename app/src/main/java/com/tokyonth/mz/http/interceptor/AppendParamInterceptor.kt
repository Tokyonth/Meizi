package com.tokyonth.mz.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AppendParamInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest
            .newBuilder()
            .addHeader("Content-Type", "text/html; charset=gb2312")
            .addHeader("Content-Type", "text/html; charset=UTF-8")
            .addHeader("Accept-Encoding", "*")
            .addHeader("Connection", "keep-alive")
            .addHeader("Accept", "*/*")
            .addHeader("Access-Control-Allow-Origin", "*")
            .addHeader("Access-Control-Allow-Headers", "X-Requested-With")
            .addHeader("Vary", "Accept-Encoding")
            .url(originalRequest.url)
            .build()
        return chain.proceed(request)
    }

}

package com.tokyonth.mz.http.url

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BaseUrlInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = getUrl(chain.request())

        val request = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(request)
    }

    private fun getUrl(request: Request): String {
        BaseUrlManager.bindMap.forEach {
            if (request.url.encodedPath == it.key) {
                val s = "${request.url.scheme}://${request.url.host}"
                return request.url.toString().replace(s, it.value)
            }
        }
        return request.url.toString()
    }

}

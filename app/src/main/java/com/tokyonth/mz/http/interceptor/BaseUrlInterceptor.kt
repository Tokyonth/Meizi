package com.tokyonth.mz.http.interceptor

import com.tokyonth.mz.http.ApiInterface
import com.tokyonth.mz.http.url.ReplaceBaseUrl

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BaseUrlInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = getUrl(chain.request())

        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "text/html; charset=gb2312")
            .addHeader("Content-Type", "text/html; charset=UTF-8")
            .addHeader("Accept-Encoding", "*")
            .addHeader("Connection", "keep-alive")
            .addHeader("Accept", "*/*")
            .addHeader("Access-Control-Allow-Origin", "*")
            .addHeader("Access-Control-Allow-Headers", "X-Requested-With")
            .addHeader("Vary", "Accept-Encoding")
            .url(newUrl)
            .build()
        return chain.proceed(request)
    }

    private fun getUrl(request: Request): String {
        return if (request.headers["replace"] == "url") {
            val apiInterface = ApiInterface::class.java
            var newUrl = ""
            for (method in apiInterface.methods) {
                val annotation = method.getAnnotation(ReplaceBaseUrl::class.java)
                if (annotation != null) {
                    val s = "${request.url.scheme}://${request.url.host}"
                    newUrl = request.url.toString().replace(s, annotation.url)
                }
            }
            newUrl
        } else {
            request.url.toString()
        }
    }

}

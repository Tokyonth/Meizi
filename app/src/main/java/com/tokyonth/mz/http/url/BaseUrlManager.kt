package com.tokyonth.mz.http.url

import android.webkit.URLUtil
import okhttp3.OkHttpClient

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

object BaseUrlManager {

    val bindMap = HashMap<String, String>()

    fun of(apiCls: Class<*>): OkHttpClient.Builder {
        analyzeBaseUrlAnnotation(apiCls)
        return OkHttpClient.Builder().apply {
            addInterceptor(BaseUrlInterceptor())
        }
    }

    private fun analyzeBaseUrlAnnotation(apiCls: Class<*>) {
        require(apiCls.isInterface) { "API declarations must be interfaces." }
        val cls = ReplaceBaseUrl::class.java
        for (method in apiCls.methods) {
            val annotation = method.getAnnotation(cls)
            method.annotations.forEach {
                if (it.toString().contains(cls.simpleName)) {
                    val path = method.annotations.find { find ->
                        find is POST || find is GET || find is PUT
                    }
                    val pathValue = when (path) {
                        is POST -> path.value
                        is GET -> path.value
                        is PUT -> path.value
                        else -> throw RuntimeException("Unknown: ${path?.javaClass?.simpleName}")
                    }
                    if (annotation != null) {
                        val url = annotation.url
                        if (URLUtil.isNetworkUrl(url)) {
                            bindMap[pathValue] = url
                        } else {
                            throw IllegalStateException("Incorrect url: $url")
                        }
                    }
                }
            }
        }
    }

}

package com.tokyonth.mz.http.url

import retrofit2.Retrofit

inline fun <reified T> Retrofit.attachBaseUrl(apiClass: Class<T>): T {
    val okhttpClient = BaseUrlManager.of(apiClass).apply {
        addInterceptor(BaseUrlInterceptor())
    }.build()
    val retrofitBuild = this.newBuilder()
        .client(okhttpClient)
        .build()
    return retrofitBuild.create(apiClass)
}

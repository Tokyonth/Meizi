package com.tokyonth.mz.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

import com.tokyonth.mz.http.interceptor.AppendParamInterceptor
import com.tokyonth.mz.http.interceptor.LogInterceptor
import com.tokyonth.mz.http.url.attachBaseUrl

class ApiRepository {

    companion object {

        val api by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ApiRepository().getApiGenerator()
        }

    }

    private fun getBaseUrl(): String {
        return "http://apis.app125.com"
    }

    private fun getApiGenerator(): ApiInterface {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(okHttpClient)
        return setRetrofitBuilder(retrofitBuilder)
            .build()
            .attachBaseUrl(ApiInterface::class.java)
        //.create(ApiInterface::class.java)
    }

    private val okHttpClient: OkHttpClient
        get() {
            return OkHttpClient.Builder().apply {
                addInterceptor(AppendParamInterceptor())
                addInterceptor(LogInterceptor())
                connectTimeout(8, TimeUnit.SECONDS)
                readTimeout(5, TimeUnit.SECONDS)
                writeTimeout(5, TimeUnit.SECONDS)
            }.build()
        }

    private fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            //addConverterFactory(SafeGsonConverterFactory.create(GsonBuilder().create()))
        }
    }

}

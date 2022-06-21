package com.tokyonth.mz.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

import com.tokyonth.mz.http.interceptor.AppendParamInterceptor
import com.tokyonth.mz.http.interceptor.BaseUrlInterceptor
import com.tokyonth.mz.http.interceptor.LogInterceptor

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
            .create(ApiInterface::class.java)
    }

    private val okHttpClient: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder().apply {
                addInterceptor(BaseUrlInterceptor())
                addInterceptor(AppendParamInterceptor())
                addInterceptor(LogInterceptor())
                connectTimeout(8, TimeUnit.SECONDS)
                readTimeout(5, TimeUnit.SECONDS)
                writeTimeout(5, TimeUnit.SECONDS)
            }
            return builder.build()
        }

    private fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            //addConverterFactory(SafeGsonConverterFactory.create(GsonBuilder().create()))
        }
    }

}

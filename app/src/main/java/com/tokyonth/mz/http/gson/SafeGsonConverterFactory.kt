package com.tokyonth.mz.http.gson

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class SafeGsonConverterFactory private constructor(private var gson: Gson?) :
    Converter.Factory() {

    init {
        if (gson == null) {
            throw IllegalStateException("Gson is null!")
        }
    }

    companion object {

        fun create(): SafeGsonConverterFactory {
            return create(Gson())
        }

        fun create(gson: Gson): SafeGsonConverterFactory {
            return SafeGsonConverterFactory(gson)
        }

    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val adapter: TypeAdapter<*> = gson!!.getAdapter(TypeToken.get(type))
        return SafeGsonResponseBodyConverter(gson!!, adapter)
    }

}

package com.tokyonth.mz.http.gson

import com.google.gson.Gson
import com.google.gson.TypeAdapter

import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import kotlin.text.Charsets.UTF_8

class SafeGsonResponseBodyConverter<T>(gson: Gson, adapter: TypeAdapter<T>) :
    Converter<ResponseBody, T> {

    private var gson: Gson? = gson
    private var adapter: TypeAdapter<T>? = adapter

    override fun convert(value: ResponseBody): T? {
        val response = value.string()
/*
        if (response.first() == '{' && response.last() == '}') {
            val json = JSONObject(response)
            if (json.has("status") && json.has("msg")) {
                if (json.get("msg") is String) {
                    value.close()
                    throw ApiException(json.getString("msg"))
                }
            }
        }
*/

        val contentType = value.contentType()
        val charset = if (contentType != null) {
            contentType.charset(UTF_8)!!
        } else {
            UTF_8
        }

        val inputStream = ByteArrayInputStream(response.toByteArray())
        val reader = InputStreamReader(inputStream, charset)
        val jsonReader = gson?.newJsonReader(reader)

        value.use {
            return adapter?.read(jsonReader)
        }
    }

}

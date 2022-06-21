package com.tokyonth.mz.http.url

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReplaceBaseUrl(
    val url: String
)

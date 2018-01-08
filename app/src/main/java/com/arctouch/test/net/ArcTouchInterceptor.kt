package com.arctouch.test.net

import java.io.IOException
import java.util.Locale

import javax.inject.Inject

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ArcTouchInterceptor @Inject
constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
                .header("charset", "utf-8")
                .method(original.method(), original.body())
                .build()
        return chain.proceed(request)
    }
}

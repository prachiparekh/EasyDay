package com.civil.easyday.app.sources.remote

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
        val request = builder.build()
        return chain.proceed(request)
    }
}
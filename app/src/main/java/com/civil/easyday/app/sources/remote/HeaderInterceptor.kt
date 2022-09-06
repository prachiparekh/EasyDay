package com.civil.easyday.app.sources.remote

import com.civil.easyday.app.sources.local.prefrences.AppPreferencesDelegates
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = AppPreferencesDelegates.get().token
        val original = chain.request()
        val builder = original.newBuilder()
            .header("Authorization", token)
        val request = builder.build()
        return chain.proceed(request)

    }
}
package com.gulsahsuluk.chocochallange.data

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRequestInterceptor @Inject constructor(private val userPreference: UserPreference) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val httpUrl = if (userPreference.getUserToken().isNullOrEmpty()) {
            request.url.newBuilder().build()
        } else {
            request.url.newBuilder()
                .addQueryParameter(API_TOKEN_QUERY, userPreference.getUserToken())
                .build()
        }

        request = request.newBuilder().url(httpUrl).build()

        return chain.proceed(request)
    }

    companion object {
        const val API_TOKEN_QUERY = "token"
    }
}
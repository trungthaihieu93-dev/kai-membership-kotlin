package com.kardia.membership.domain.network

import com.kardia.membership.data.cache.UserTokenCache
import com.kardia.membership.features.utils.AppLog
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class ServiceInterceptor constructor(
    private val userTokenCache: UserTokenCache
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val headerKey = request.header("header-key")

        if (request.header("No-Authentication") == null) {
            val finalToken = "Bearer ${userTokenCache.get()?.accessToken}"
            AppLog.e("Duy", "$finalToken")
            request = request.newBuilder()
                .addHeader("Authorization", finalToken)
                .build()
        } else {
            request = request.newBuilder()
                .build()
        }

        return chain.proceed(request)
    }

}
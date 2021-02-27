package com.kardia.membership.domain.network

import android.util.Log
import com.kardia.membership.core.exception.Failure
import com.kardia.membership.core.extension.TAG
import com.kardia.membership.core.functional.Either
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.domain.entities.BaseEntities
import com.kardia.membership.features.utils.AppLog
import com.google.gson.Gson
import retrofit2.Call
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseNetwork {
    internal fun <T, R> request(
        call: Call<T>,
        transform: (T) -> R,
        default: T?
    ): Either<Failure, R> {
        return try {
            BaseActivity.apiRequestCount++
            val response = call.execute()
            Log.d(TAG, response.body().toString())
            BaseActivity.apiRequestCount--
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default!!)))
                false -> {
                    val errorBody =
                        response.errorBody()?.string()?.replace("\"data\":\"\"", "\"data\":{}")
                    AppLog.e("OkHttpClient - Duy", "$errorBody")
                    var code: Int = response.code()
                    var message: String = ""
//                    try {
//                        Gson().fromJson(errorBody, SignInUpEntity::class.java)?.message_code?.let {
//                            code = it
//                        }
//                    } catch (e: Exception) {
//                    }

                    Gson().fromJson(errorBody, BaseEntities::class.java)?.let { message = it.error }

                    Either.Left(Failure.ServerError(message, code))
                }
            }

        } catch (exception: Throwable) {
            BaseActivity.apiRequestCount--
            Log.d(TAG, "MINUS ERROR---"+BaseActivity.apiRequestCount.toString())
            if (exception is SocketTimeoutException) {
                Either.Left(
                    Failure.ServerError(
                        "Sorry, the server timed out. Please try again.",
                        -1
                    )
                )
            } else if (exception is UnknownHostException) {
                Either.Left(Failure.ServerError("The server is down.", -1))
            } else {
                Either.Left(
                    Failure.ServerError(
                        "An upgrade is currently in progress. Please try again later",
                        -1
                    )
                )
            }
        }
    }
}
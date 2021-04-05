package com.kardia.membership.domain.api

import com.kardia.membership.domain.entities.wallet.SendKAIEntity
import com.kardia.membership.domain.usecases.wallet.PostSendKAIUseCase
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

internal interface WalletAPI {
    companion object {
        private const val SEND = "v1/wallet/send"
    }

    @POST(SEND)
    fun sendKAI(@Body params: PostSendKAIUseCase.Params): Call<SendKAIEntity>
}
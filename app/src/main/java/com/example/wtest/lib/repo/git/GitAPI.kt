package com.example.wtest.lib.repo.git

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming

/**
 * API interface to Git URL calls and to be used by [Retrofit] to generate a class that implements it
 */
interface GitAPI {
    @GET("centraldedados/codigos_postais/raw/master/data/codigos_postais.csv")
    @Streaming
    suspend fun zipCodes(): Response<ResponseBody>
}
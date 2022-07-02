package com.example.wtest.lib.repo.git

import okhttp3.ResponseBody
import retrofit2.Response

/**
 * API wrap class for
 */
class Git(
    private val api: GitAPI
) {
    suspend fun zipCodes(): Response<ResponseBody> = api.zipCodes()
}
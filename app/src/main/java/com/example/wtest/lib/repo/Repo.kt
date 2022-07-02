package com.example.wtest.lib.repo

import com.example.wtest.lib.repo.room.Address
import com.example.wtest.lib.repo.room.AddressFTS
import com.example.wtest.lib.repo.room.AddressWithMatchInfo
import kotlinx.coroutines.flow.Flow

/**
 * An interface for of the
 */
interface Repo {
    /**
     * Call to fecth zip codes from [Git] URL
     */
    fun zipCodes(): Flow<Result<Unit>>

    /**
     * Call to execute a fulltext search on the local database using a query string and return the result
     */
    suspend fun search(query: String): Flow<List<Address>>
}
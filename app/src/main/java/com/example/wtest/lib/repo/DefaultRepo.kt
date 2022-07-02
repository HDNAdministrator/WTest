package com.example.wtest.lib.repo

import com.example.wtest.lib.repo.git.Git
import com.example.wtest.lib.repo.room.Address
import com.example.wtest.lib.repo.room.LocalSQL
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import org.apache.commons.csv.CSVFormat

/**
 * Default implementation of [Repo]
 */
class DefaultRepo(
    private val csvFormat: CSVFormat,
    private val git: Git,
    private val localSQL: LocalSQL
) : Repo {

    //region SPLASH VIEW MODEL
    /**
     * Evaluate if the database is filled. If not it will fetch and fill it
     */
    override fun zipCodes(): Flow<Result<Unit>> {
        return channelFlow {
            try {
                if (localSQL.addressDao().existData()) { send(Result.success(Unit)) }
                else {
                    git
                        .zipCodes()
                        .body()
                        ?.byteStream()
                        ?.bufferedReader()
                        ?.use { csvFormat.parse(it).records }?.map { Address.from(it) }?.let { localSQL.addressDao().insertAll(it) }
                        ?.run { send(Result.success(Unit)) }
                        ?: send(Result.failure(Exception("Fail")))
                }
            }
            catch (e: Exception) { send(Result.failure(e)) }
        }.flowOn(IO)
    }
    //endregion SPLASH VIEW MODEL

    //region SEARCH VIEW MODEL
    /**
     * Execute a fulltext search over the datatable using the provided query string and returns a [Flow] with the result [List]
     */
    override suspend fun search(query: String): Flow<List<Address>> {
        return channelFlow {
            send(localSQL.addressDao().findByMatch(query))
        }.flowOn(Default)
    }
    //endregion SEARCH VIEW MODEL
}
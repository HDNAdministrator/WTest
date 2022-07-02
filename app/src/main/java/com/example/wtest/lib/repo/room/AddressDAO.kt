package com.example.wtest.lib.repo.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

/**
 * The DAO interface used by the app to interact with the database
 */
@Dao
interface AddressDAO {

    /**
     * Returns a full-text search result [List] with matching then [Address]es to a given query string
     */
    @Query("SELECT * FROM address INNER JOIN address_fts ON address.rowid = address_fts.rowid WHERE address_fts MATCH :query")
    suspend fun findByMatch(query: String): List<Address>

    /**
     * Evaluate if there exists any data on the address table
     */
    @Query("SELECT EXISTS (SELECT * FROM address)")
    suspend fun existData(): Boolean

    @Insert(onConflict = REPLACE)
    fun insertAll(addresses: List<Address>)

    @Insert(onConflict = REPLACE)
    fun insert(address: Address)

    @Query("DELETE FROM address")
    fun truncate()
}
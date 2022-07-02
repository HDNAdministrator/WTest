package com.example.wtest.lib.repo.room

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class AddressWithMatchInfo(
    @Embedded val address: Address,
    @ColumnInfo(name = "matchInfo") val matchInfo: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AddressWithMatchInfo

        if (address != other.address) return false
        if (!matchInfo.contentEquals(other.matchInfo)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = address.hashCode()

        result = 31 * result + matchInfo.contentHashCode()

        return result
    }
}

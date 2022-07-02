package com.example.wtest.lib.repo.room

import androidx.room.*
import com.example.wtest.lib.annotations.Field.Companion.DESIG_POSTAL
import com.example.wtest.lib.annotations.Field.Companion.EXT_CODE_POSTAL
import com.example.wtest.lib.annotations.Field.Companion.NUM_COD_POSTAL
import com.example.wtest.lib.annotations.Field.Companion.ROW_ID


@Entity(tableName = "address_fts")
@Fts4(contentEntity = Address::class)
data class AddressFTS(
//    @PrimaryKey @ColumnInfo(name = ROW_ID) val rowid : Int,
    @ColumnInfo(name = NUM_COD_POSTAL) val numCodPostal: String,
    @ColumnInfo(name = EXT_CODE_POSTAL) val extCodPostal: String,
    @ColumnInfo(name = DESIG_POSTAL) val desigPostal: String
) {
    @Ignore
    override fun toString(): String = "$numCodPostal-$extCodPostal, $desigPostal"
}
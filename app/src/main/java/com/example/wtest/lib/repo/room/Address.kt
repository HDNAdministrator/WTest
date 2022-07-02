package com.example.wtest.lib.repo.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.wtest.lib.annotations.Field
import com.example.wtest.lib.annotations.Field.Companion.CLIENTE
import com.example.wtest.lib.annotations.Field.Companion.COD_ARTERIA
import com.example.wtest.lib.annotations.Field.Companion.COD_CONCELHO
import com.example.wtest.lib.annotations.Field.Companion.COD_DISTRITO
import com.example.wtest.lib.annotations.Field.Companion.COD_LOCALIDADE
import com.example.wtest.lib.annotations.Field.Companion.DESIG_POSTAL
import com.example.wtest.lib.annotations.Field.Companion.EXT_CODE_POSTAL
import com.example.wtest.lib.annotations.Field.Companion.LOCAL_ARTERIA
import com.example.wtest.lib.annotations.Field.Companion.NOME_ARTERIA
import com.example.wtest.lib.annotations.Field.Companion.NOME_LOCALIDADE
import com.example.wtest.lib.annotations.Field.Companion.NUM_COD_POSTAL
import com.example.wtest.lib.annotations.Field.Companion.PORTA
import com.example.wtest.lib.annotations.Field.Companion.PREP_1
import com.example.wtest.lib.annotations.Field.Companion.PREP_2
import com.example.wtest.lib.annotations.Field.Companion.ROW_ID
import com.example.wtest.lib.annotations.Field.Companion.TIPO_ARTERIA
import com.example.wtest.lib.annotations.Field.Companion.TITULO_ARTERIA
import com.example.wtest.lib.annotations.Field.Companion.TROCO
import org.apache.commons.csv.CSVRecord

/**
 * This class represents ad both data schema of the table `address` as data class in queries results
 */
@Entity(tableName = "address")
data class Address(
    @PrimaryKey @ColumnInfo(name = ROW_ID) val rowid : Int,
    @ColumnInfo(name = COD_DISTRITO) val codDistrito: String?,
    @ColumnInfo(name = COD_CONCELHO) val codConcelho: String?,
    @ColumnInfo(name = COD_LOCALIDADE) val codLocalidade: String?,
    @ColumnInfo(name = NOME_LOCALIDADE) val nomeLocalidade: String?,
    @ColumnInfo(name = COD_ARTERIA) val codArteria: String?,
    @ColumnInfo(name = TIPO_ARTERIA) val tipoArteria: String?,
    @ColumnInfo(name = PREP_1) val prep1: String?,
    @ColumnInfo(name = TITULO_ARTERIA) val tituloArteria: String?,
    @ColumnInfo(name = PREP_2) val prep2: String?,
    @ColumnInfo(name = NOME_ARTERIA) val nomeArteria: String,
    @ColumnInfo(name = LOCAL_ARTERIA) val localArteria: String?,
    @ColumnInfo(name = TROCO) val troco: String?,
    @ColumnInfo(name = PORTA) val porta: String?,
    @ColumnInfo(name = CLIENTE) val cliente: String?,
    @ColumnInfo(name = NUM_COD_POSTAL) val numCodPostal: String,
    @ColumnInfo(name = EXT_CODE_POSTAL) val extCodPostal: String,
    @ColumnInfo(name = DESIG_POSTAL) val desigPostal: String
) {
    companion object {
        fun from(csvRecord: CSVRecord): Address {
            with(csvRecord) {
                return Address(
                    rowid = recordNumber.toInt(),
                    codDistrito = get(COD_DISTRITO).toString(),
                    codConcelho = get(COD_CONCELHO),
                    codLocalidade = get(COD_LOCALIDADE),
                    nomeLocalidade = get(NOME_LOCALIDADE),
                    codArteria = get(COD_ARTERIA),
                    tipoArteria = get(TIPO_ARTERIA),
                    prep1 = get(PREP_1),
                    tituloArteria = get(TITULO_ARTERIA),
                    prep2 = get(PREP_2),
                    nomeArteria = get(NOME_ARTERIA),
                    localArteria = get(LOCAL_ARTERIA),
                    troco = get(TROCO),
                    porta = get(PORTA),
                    cliente = get(CLIENTE),
                    numCodPostal = get(NUM_COD_POSTAL),
                    extCodPostal = get(EXT_CODE_POSTAL),
                    desigPostal = get(DESIG_POSTAL)
                )
            }
        }
    }
    
    @Ignore
    override fun toString(): String = "$numCodPostal-$extCodPostal, $desigPostal"
}
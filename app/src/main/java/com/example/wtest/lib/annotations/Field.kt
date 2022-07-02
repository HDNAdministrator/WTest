package com.example.wtest.lib.annotations

import androidx.annotation.StringDef

/**
 * Annotation with string constants for general usage within the app
 */
@Retention(AnnotationRetention.SOURCE)
@StringDef
annotation class Field {
    companion object {
        const val ROW_ID = "rowid"
        const val COD_DISTRITO = "cod_distrito"
        const val COD_CONCELHO = "cod_concelho"
        const val COD_LOCALIDADE = "cod_localidade"
        const val NOME_LOCALIDADE = "nome_localidade"
        const val COD_ARTERIA = "cod_arteria"
        const val TIPO_ARTERIA = "tipo_arteria"
        const val PREP_1 = "prep1"
        const val TITULO_ARTERIA = "titulo_arteria"
        const val PREP_2 = "prep2"
        const val NOME_ARTERIA = "nome_arteria"
        const val LOCAL_ARTERIA = "local_arteria"
        const val TROCO = "troco"
        const val PORTA = "porta"
        const val CLIENTE = "cliente"
        const val NUM_COD_POSTAL = "num_cod_postal"
        const val EXT_CODE_POSTAL = "ext_cod_postal"
        const val DESIG_POSTAL = "desig_postal"
    }
}
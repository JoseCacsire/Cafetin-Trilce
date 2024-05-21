package com.yostin.cafetin.Esquema

import android.provider.BaseColumns

class CategoryContract {
    companion object {
        object Entrada : BaseColumns {
            const val NOMBRE_TABLA = "category"
            const val COLUMNA_ID = "idCategory"
            const val COLUMNA_NOMBRE = "nombreCategory"
            const val COLUMNA_IMAGEN_URI = "imagenUri"
        }
    }
}

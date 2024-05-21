package com.yostin.cafetin.Esquema

import android.provider.BaseColumns

class ProductosContract {
    companion object{
        class Entrada: BaseColumns{
            companion object{
                val NOMBRE_TABLA = "productos"
                val COLUMNA_ID = "idProducto"
                val COLUMNA_NOMBRE = "nombreProducto"
                val COLUMNA_PRECIO = "precioProducto"
                val COLUMNA_STOCK = "stockProducto"
                val COLUMNA_DESCRIPCION = "descripcionProducto"
                val COLUMNA_CATEGORIA_PRODUCTO = "categoryProducto"
                val COLUMNA_IMAGEN_URI = "imageUri"
            }
        }
    }
}
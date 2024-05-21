package com.yostin.cafetin.Esquema

import android.provider.BaseColumns

class DetalleContract {
    companion object {
        object Entrada : BaseColumns {
            const val NOMBRE_TABLA = "detalle"
            const val COLUMNA_ID = "idDetalle"
            const val COLUMNA_PEDIDO_DETALLE = "pedidoDetalle"
            const val COLUMNA_NOMBRE_PRODUCTO = "nameProductoPedido"
            const val COLUMNA_CANTIDAD_PRODUCTO = "productQuantityPedido"
        }
    }
}
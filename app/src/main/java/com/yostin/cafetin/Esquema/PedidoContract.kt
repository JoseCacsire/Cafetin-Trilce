package com.yostin.cafetin.Esquema

import android.provider.BaseColumns

class PedidoContract {
    companion object {
        object Entrada : BaseColumns {
            const val NOMBRE_TABLA = "pedido"
            const val COLUMNA_ID = "idPedido"
            const val COLUMNA_ID_USER_PEDIDO = "UserPedido"
            const val COLUMNA_DATE = "fechaRegistro"
            const val COLUMNA_ESTADO = "estadoPedido"
            const val COLUMNA_MONTO_ = "monto"
        }
    }
}
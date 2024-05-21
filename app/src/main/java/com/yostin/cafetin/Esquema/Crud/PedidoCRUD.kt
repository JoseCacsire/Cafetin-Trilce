package com.yostin.cafetin.Esquema.Crud

import android.content.ContentValues
import android.content.Context
import com.yostin.cafetin.Entity.Detalle
import com.yostin.cafetin.Entity.Pedido
import com.yostin.cafetin.Esquema.DetalleContract
import com.yostin.cafetin.Esquema.Helper.DataBaseHelper
import com.yostin.cafetin.Esquema.PedidoContract
import com.yostin.cafetin.Esquema.UserContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PedidoCRUD(context: Context) {
    private val db = DataBaseHelper(context)

    suspend fun insertarPedidoConDetalles(pedido: Pedido, detalles: List<Detalle>) {
        withContext(Dispatchers.IO) {
            val pedidoId = insertarPedido(pedido)

            detalles.forEach { detalle ->
                detalle.pedidoDetalle = pedidoId.toInt()
                insertarDetalle(detalle)
            }
        }
    }

    private fun insertarPedido(pedido: Pedido): Long {
        val db = db.writableDatabase
        val values = ContentValues().apply {
            put("userPedido", pedido.userPedido)
            put("fechaRegistro", pedido.fechaRegistro)
            put("estadoPedido", pedido.estadoPedido)
            put("monto", pedido.monto)
        }
        return db.insert("Pedido", null, values)
    }

    private fun insertarDetalle(detalle: Detalle) {
        val db = db.writableDatabase
        val values = ContentValues().apply {
            put("pedidoDetalle", detalle.pedidoDetalle)
            put("nameProductoPedido", detalle.nameProductoPedido)
            put("productQuantityPedido", detalle.productQuantityPedido)
        }
        db.insert("Detalle", null, values)
    }

    suspend fun listarPedidosConNombreUsuarioPorIdUsuario(idUsuario: Int): List<Pair<Pedido, String>> {
        return withContext(Dispatchers.IO) {
            val pedidosConNombreUsuario = mutableListOf<Pair<Pedido, String>>()
            val db = db.readableDatabase

            // Realizamos un INNER JOIN entre las tablas Pedido y User para obtener el nombre del usuario
            val query = """
            SELECT p.${PedidoContract.Companion.Entrada.COLUMNA_ID},
                   p.${PedidoContract.Companion.Entrada.COLUMNA_DATE},
                   p.${PedidoContract.Companion.Entrada.COLUMNA_ESTADO},
                   p.${PedidoContract.Companion.Entrada.COLUMNA_MONTO_},
                   u.${UserContract.Companion.Entrada.COLUMNA_NOMBRE_USER}
            FROM ${PedidoContract.Companion.Entrada.NOMBRE_TABLA} p
            INNER JOIN ${UserContract.Companion.Entrada.NOMBRE_TABLA} u
            ON p.${PedidoContract.Companion.Entrada.COLUMNA_ID_USER_PEDIDO} = u.${UserContract.Companion.Entrada.COLUMNA_ID}
            WHERE p.${PedidoContract.Companion.Entrada.COLUMNA_ID_USER_PEDIDO} = ?
        """.trimIndent()

            val cursor = db.rawQuery(query, arrayOf(idUsuario.toString()))

            while (cursor.moveToNext()) {
                val idPedido = cursor.getInt(cursor.getColumnIndexOrThrow(PedidoContract.Companion.Entrada.COLUMNA_ID))
                val fechaRegistro = cursor.getString(cursor.getColumnIndexOrThrow(PedidoContract.Companion.Entrada.COLUMNA_DATE))
                val estadoPedido = cursor.getString(cursor.getColumnIndexOrThrow(PedidoContract.Companion.Entrada.COLUMNA_ESTADO))
                val monto = cursor.getDouble(cursor.getColumnIndexOrThrow(PedidoContract.Companion.Entrada.COLUMNA_MONTO_))
                val nombreUsuario = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.Companion.Entrada.COLUMNA_NOMBRE_USER))

                // Creamos el Pedido y lo agregamos junto con el nombre del usuario a la lista
                val pedido = Pedido(idPedido, idUsuario, fechaRegistro, estadoPedido, monto)
                val pedidoConNombreUsuario = Pair(pedido, nombreUsuario)
                pedidosConNombreUsuario.add(pedidoConNombreUsuario)
            }
            cursor.close()

            pedidosConNombreUsuario
        }
    }

    suspend fun listarPedidos(): List<Pair<Pedido, String>> {
        return withContext(Dispatchers.IO) {
            val pedidosConNombreUsuario = mutableListOf<Pair<Pedido, String>>()
            val db = db.readableDatabase

            // Realizamos un INNER JOIN entre las tablas Pedido y User para obtener el nombre del usuario
            val query = """
        SELECT p.${PedidoContract.Companion.Entrada.COLUMNA_ID},
               p.${PedidoContract.Companion.Entrada.COLUMNA_DATE},
               p.${PedidoContract.Companion.Entrada.COLUMNA_ESTADO},
               p.${PedidoContract.Companion.Entrada.COLUMNA_MONTO_},
               u.${UserContract.Companion.Entrada.COLUMNA_NOMBRE_USER}
        FROM ${PedidoContract.Companion.Entrada.NOMBRE_TABLA} p
        INNER JOIN ${UserContract.Companion.Entrada.NOMBRE_TABLA} u
        ON p.${PedidoContract.Companion.Entrada.COLUMNA_ID_USER_PEDIDO} = u.${UserContract.Companion.Entrada.COLUMNA_ID}
    """.trimIndent()

            val cursor = db.rawQuery(query, null)

            while (cursor.moveToNext()) {
                val idPedido =
                    cursor.getInt(cursor.getColumnIndexOrThrow(PedidoContract.Companion.Entrada.COLUMNA_ID))
                val fechaRegistro =
                    cursor.getString(cursor.getColumnIndexOrThrow(PedidoContract.Companion.Entrada.COLUMNA_DATE))
                val estadoPedido =
                    cursor.getString(cursor.getColumnIndexOrThrow(PedidoContract.Companion.Entrada.COLUMNA_ESTADO))
                val monto =
                    cursor.getDouble(cursor.getColumnIndexOrThrow(PedidoContract.Companion.Entrada.COLUMNA_MONTO_))
                val nombreUsuario =
                    cursor.getString(cursor.getColumnIndexOrThrow(UserContract.Companion.Entrada.COLUMNA_NOMBRE_USER))

                // Creamos el Pedido y lo agregamos junto con el nombre del usuario a la lista
                val pedido = Pedido(idPedido,null, fechaRegistro, estadoPedido, monto)
                val pedidoConNombreUsuario = Pair(pedido, nombreUsuario)
                pedidosConNombreUsuario.add(pedidoConNombreUsuario)
            }
            cursor.close()

            pedidosConNombreUsuario
        }
    }

    fun actualizarEstadoPedidoPorId(idPedido: Int?, nuevoEstado: String?) {
        val db = db.writableDatabase
        val values = ContentValues().apply {
            put("estadoPedido", nuevoEstado)
        }
        val whereClause = "idPedido = ?"
        val whereArgs = arrayOf(idPedido.toString())
        db.update("Pedido", values, whereClause, whereArgs)
    }

    suspend fun recuperarDetallesPorIdPedido(idPedido: Int): List<Detalle> {
        return withContext(Dispatchers.IO) {
            val detallesPedido = mutableListOf<Detalle>()
            val db = db.readableDatabase

            val query = """
            SELECT ${DetalleContract.Companion.Entrada.COLUMNA_NOMBRE_PRODUCTO},
                   ${DetalleContract.Companion.Entrada.COLUMNA_CANTIDAD_PRODUCTO}
            FROM ${DetalleContract.Companion.Entrada.NOMBRE_TABLA}
            WHERE ${DetalleContract.Companion.Entrada.COLUMNA_PEDIDO_DETALLE} = ?
        """.trimIndent()

            val cursor = db.rawQuery(query, arrayOf(idPedido.toString()))

            while (cursor.moveToNext()) {
                val nombreProducto = cursor.getString(cursor.getColumnIndexOrThrow(DetalleContract.Companion.Entrada.COLUMNA_NOMBRE_PRODUCTO))
                val cantidadProducto = cursor.getInt(cursor.getColumnIndexOrThrow(DetalleContract.Companion.Entrada.COLUMNA_CANTIDAD_PRODUCTO))

                val detalle = Detalle(null, idPedido, nombreProducto, cantidadProducto)
                detallesPedido.add(detalle)
            }
            cursor.close()

            detallesPedido
        }
    }

}
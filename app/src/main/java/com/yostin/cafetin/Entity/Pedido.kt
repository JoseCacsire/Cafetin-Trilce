package com.yostin.cafetin.Entity

import java.util.Date

class Pedido(
    var idPedido: Int?,
    var userPedido: Int?,
    var fechaRegistro: String?,
    var estadoPedido: String?,
    var monto: Double?) {
    constructor(): this(null, null, null, null, null)
}
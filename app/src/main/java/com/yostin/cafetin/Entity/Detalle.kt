package com.yostin.cafetin.Entity

class Detalle (
    var idDetalle: Int?,
    var pedidoDetalle: Int?,
    var nameProductoPedido: String?,
    var productQuantityPedido: Int?
){
    constructor(): this(null, null, null, null)
}
package com.yostin.cafetin.Entity

class Producto(
    var idProducto: Int?,
    var nombreProducto: String?,
    var precioProducto: Double?,
    var stockProducto: Int?,
    var categoryProducto: Int?,
    var descripcionProducto: String?,
    var imageUri: String?
) {

    constructor() : this(null, null, null, null, null, null, null)
    constructor(nombreProducto: String?, imagenProducto: String?, currentStock: Int?, precioProducto: Double?) : this(null, nombreProducto, precioProducto, currentStock, null, null, imagenProducto)
}
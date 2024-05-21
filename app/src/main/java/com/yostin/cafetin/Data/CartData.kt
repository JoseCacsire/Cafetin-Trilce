package com.yostin.cafetin.Data

data class CartData(
    val productName: String,
    val productPrice: Double,
    var productQuantity: Int,
    val productImageUri: String
)



package com.yostin.cafetin.Entity

class User (
    var idUser: Int?,
    var nombreUser: String?,
    var emailUser: String?,
    var phoneUser: String?,
    var passwordUser: String?
) {
    constructor(): this(null, null, null, null, null)
}


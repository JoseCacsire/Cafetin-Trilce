package com.yostin.cafetin.Esquema

import android.provider.BaseColumns

class UserContract {
    companion object {
        object Entrada: BaseColumns{
            const val NOMBRE_TABLA = "user"
            const val COLUMNA_ID = "idUser"
            const val COLUMNA_NOMBRE_USER = "nombreUser"
            const val COLUMNA_EMAIL_USER = "emailUser"
            const val COLUMNA_PHONE_USER = "phoneUser"
            const val COLUMNA_PASSWORD = "passwordUser"
        }
    }
}
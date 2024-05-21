package com.yostin.cafetin.Esquema.Crud

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.yostin.cafetin.Entity.Category
import com.yostin.cafetin.Entity.User
import com.yostin.cafetin.Esquema.CategoryContract
import com.yostin.cafetin.Esquema.Helper.DataBaseHelper
import com.yostin.cafetin.Esquema.ProductosContract
import com.yostin.cafetin.Esquema.UserContract

class UserCRUD(context: Context) {
    private var helper: DataBaseHelper? = null

    init{
        helper = DataBaseHelper(context)
    }

    fun newUser(item: User) {
        val db: SQLiteDatabase = helper?.writableDatabase!!
        val values = ContentValues()
        values.put(UserContract.Companion.Entrada.COLUMNA_NOMBRE_USER, item.nombreUser)
        values.put(UserContract.Companion.Entrada.COLUMNA_EMAIL_USER, item.emailUser)
        values.put(UserContract.Companion.Entrada.COLUMNA_PHONE_USER, item.phoneUser)
        values.put(UserContract.Companion.Entrada.COLUMNA_PASSWORD, item.passwordUser)
        val newRowId = db.insert(UserContract.Companion.Entrada.NOMBRE_TABLA,
            null, values)
        db.close()
    }

    fun getUserIdByEmail(email: String): Int? {
        val db: SQLiteDatabase = helper?.readableDatabase ?: return null
        val projection = arrayOf(UserContract.Companion.Entrada.COLUMNA_ID)
        val selection = "${UserContract.Companion.Entrada.COLUMNA_EMAIL_USER} = ?"
        val selectionArgs = arrayOf(email)

        val cursor = db.query(
            UserContract.Companion.Entrada.NOMBRE_TABLA,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var userId: Int? = null

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(UserContract.Companion.Entrada.COLUMNA_ID)
            if (columnIndex != -1) {
                userId = cursor.getInt(columnIndex)
            } else {
                Log.d("UserCrud", "No hay datos")
            }
        }
        cursor.close()
        db.close()

        return userId
    }
}
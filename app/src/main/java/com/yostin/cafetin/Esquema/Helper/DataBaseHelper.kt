package com.yostin.cafetin.Esquema.Helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.yostin.cafetin.Esquema.CategoryContract
import com.yostin.cafetin.Esquema.DetalleContract
import com.yostin.cafetin.Esquema.PedidoContract
import com.yostin.cafetin.Esquema.ProductosContract
import com.yostin.cafetin.Esquema.UserContract

class DataBaseHelper(context: Context): SQLiteOpenHelper(context, "TrilceDB",null, 16) {

    companion object{
        val CREATE_USER_TABLE = "CREATE TABLE " + UserContract.Companion.Entrada.NOMBRE_TABLA +
                " (" + UserContract.Companion.Entrada.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserContract.Companion.Entrada.COLUMNA_NOMBRE_USER + " TEXT, " +
                UserContract.Companion.Entrada.COLUMNA_EMAIL_USER + " TEXT, " +
                UserContract.Companion.Entrada.COLUMNA_PHONE_USER + " TEXT, " +
                UserContract.Companion.Entrada.COLUMNA_PASSWORD + " TEXT )"

        val REMOVE_USER_TABLE = "DROP TABLE IF EXISTS " + UserContract.Companion.Entrada.NOMBRE_TABLA


        val CREATE_CATEGORY_TABLE = "CREATE TABLE " + CategoryContract.Companion.Entrada.NOMBRE_TABLA +
                " (" + CategoryContract.Companion.Entrada.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoryContract.Companion.Entrada.COLUMNA_NOMBRE + " TEXT, " +
                CategoryContract.Companion.Entrada.COLUMNA_IMAGEN_URI + " TEXT )"
        val REMOVE_CATEGORY_TABLE = "DROP TABLE IF EXISTS " + CategoryContract.Companion.Entrada.NOMBRE_TABLA

        val CREATE_PRODUCTOS_TABLE = "CREATE TABLE " + ProductosContract.Companion.Entrada.NOMBRE_TABLA +
                " (" + ProductosContract.Companion.Entrada.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProductosContract.Companion.Entrada.COLUMNA_NOMBRE + " TEXT, " +
                ProductosContract.Companion.Entrada.COLUMNA_PRECIO + " REAL, " +
                ProductosContract.Companion.Entrada.COLUMNA_STOCK + " INTEGER, " +
                ProductosContract.Companion.Entrada.COLUMNA_CATEGORIA_PRODUCTO + " INTEGER, "+
                ProductosContract.Companion.Entrada.COLUMNA_DESCRIPCION + " TEXT," +
                ProductosContract.Companion.Entrada.COLUMNA_IMAGEN_URI + " TEXT, " +
                "FOREIGN KEY(" + ProductosContract.Companion.Entrada.COLUMNA_CATEGORIA_PRODUCTO + ") REFERENCES " +
                CategoryContract.Companion.Entrada.NOMBRE_TABLA + " ( " + CategoryContract.Companion.Entrada.COLUMNA_ID + " ) )"

        val REMOVE_PRODUCTOS_TABLE = "DROP TABLE IF EXISTS " + ProductosContract.Companion.Entrada.NOMBRE_TABLA

        val CREATE_PEDIDOS_TABLE = "CREATE TABLE " + PedidoContract.Companion.Entrada.NOMBRE_TABLA + " (" +
                        PedidoContract.Companion.Entrada.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PedidoContract.Companion.Entrada.COLUMNA_ID_USER_PEDIDO + " INTEGER, " +
                        PedidoContract.Companion.Entrada.COLUMNA_DATE + " TEXT, " +
                        PedidoContract.Companion.Entrada.COLUMNA_ESTADO + " TEXT, " +
                        PedidoContract.Companion.Entrada.COLUMNA_MONTO_ + " REAL, " +
                        "FOREIGN KEY(" + PedidoContract.Companion.Entrada.COLUMNA_ID_USER_PEDIDO + ") REFERENCES " +
                        UserContract.Companion.Entrada.NOMBRE_TABLA + "(" + UserContract.Companion.Entrada.COLUMNA_ID + ") )"

        val REMOVE_PEDIDOS_TABLE = "DROP TABLE IF EXISTS " + PedidoContract.Companion.Entrada.NOMBRE_TABLA

        val CREATE_DETALLES_TABLE =
                "CREATE TABLE " + DetalleContract.Companion.Entrada.NOMBRE_TABLA + " (" +
                        DetalleContract.Companion.Entrada.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DetalleContract.Companion.Entrada.COLUMNA_PEDIDO_DETALLE + " INTEGER, " +
                        DetalleContract.Companion.Entrada.COLUMNA_NOMBRE_PRODUCTO + " TEXT, " +
                        DetalleContract.Companion.Entrada.COLUMNA_CANTIDAD_PRODUCTO + " INTEGER, " +
                        "FOREIGN KEY(" + DetalleContract.Companion.Entrada.COLUMNA_PEDIDO_DETALLE + ") REFERENCES " +
                        PedidoContract.Companion.Entrada.NOMBRE_TABLA + "(" + PedidoContract.Companion.Entrada.COLUMNA_ID + "))"

        val REMOVE_DETALLES_TABLE = "DROP TABLE IF EXISTS " + DetalleContract.Companion.Entrada.NOMBRE_TABLA
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_USER_TABLE)
        db?.execSQL(CREATE_CATEGORY_TABLE)
        db?.execSQL(CREATE_PRODUCTOS_TABLE)
        db?.execSQL(CREATE_PEDIDOS_TABLE)
        db?.execSQL(CREATE_DETALLES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, i: Int, i2: Int) {
        db?.execSQL(REMOVE_USER_TABLE)
        db?.execSQL(REMOVE_CATEGORY_TABLE)
        db?.execSQL(REMOVE_PRODUCTOS_TABLE)
        db?.execSQL(REMOVE_PEDIDOS_TABLE)
        db?.execSQL(REMOVE_DETALLES_TABLE)
        onCreate(db)
    }
}
package com.yostin.cafetin.Esquema.Crud

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.yostin.cafetin.Entity.Category
import com.yostin.cafetin.Entity.Producto
import com.yostin.cafetin.Esquema.CategoryContract
import com.yostin.cafetin.Esquema.Helper.DataBaseHelper
import com.yostin.cafetin.Esquema.ProductosContract

class ProductoCRUD (context: Context) {

    private var helper : DataBaseHelper? = null

    init {
        helper = DataBaseHelper(context)
    }

    fun newProduct(item:Producto){
        val db: SQLiteDatabase = helper?.writableDatabase!!
        val values = ContentValues()
        values.put(ProductosContract.Companion.Entrada.COLUMNA_ID,item.idProducto)
        values.put(ProductosContract.Companion.Entrada.COLUMNA_NOMBRE, item.nombreProducto)
        values.put(ProductosContract.Companion.Entrada.COLUMNA_PRECIO, item.precioProducto)
        values.put(ProductosContract.Companion.Entrada.COLUMNA_STOCK, item.stockProducto)
        values.put(ProductosContract.Companion.Entrada.COLUMNA_CATEGORIA_PRODUCTO, item.categoryProducto)
        values.put(ProductosContract.Companion.Entrada.COLUMNA_DESCRIPCION, item.descripcionProducto)
        values.put(ProductosContract.Companion.Entrada.COLUMNA_IMAGEN_URI, item.imageUri)
        val newRowId = db.insert(ProductosContract.Companion.Entrada.NOMBRE_TABLA,null, values)
        db.close()
    }

    fun getProducts(): ArrayList<Producto>{

        val items:ArrayList<Producto> = ArrayList()

        val db: SQLiteDatabase = helper?.readableDatabase!!

        val columnas = arrayOf(ProductosContract.Companion.Entrada.COLUMNA_ID, ProductosContract.Companion.Entrada.COLUMNA_NOMBRE,
            ProductosContract.Companion.Entrada.COLUMNA_PRECIO, ProductosContract.Companion.Entrada.COLUMNA_STOCK,
            ProductosContract.Companion.Entrada.COLUMNA_CATEGORIA_PRODUCTO, ProductosContract.Companion.Entrada.COLUMNA_DESCRIPCION,
            ProductosContract.Companion.Entrada.COLUMNA_IMAGEN_URI)

        val c: Cursor = db.query(
            ProductosContract.Companion.Entrada.NOMBRE_TABLA,
            columnas,
            null,
            null,
            null,
            null,
            null
        )
        while (c.moveToNext()) {
            items.add(
                Producto(
                    c.getInt(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_NOMBRE)),
                    c.getDouble(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_PRECIO)),
                    c.getInt(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_STOCK)),
                    c.getInt(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_CATEGORIA_PRODUCTO)),
                    c.getString(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_DESCRIPCION)),
                    c.getString(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_IMAGEN_URI))
                )
            )
        }
        c.close()
        return items
    }

    fun getProductsByCategoryId(categoryId: Int): ArrayList<Producto> {
        val items: ArrayList<Producto> = ArrayList()
        val db: SQLiteDatabase = helper?.readableDatabase!!
        val columnas = arrayOf(
            ProductosContract.Companion.Entrada.COLUMNA_ID,
            ProductosContract.Companion.Entrada.COLUMNA_NOMBRE,
            ProductosContract.Companion.Entrada.COLUMNA_PRECIO,
            ProductosContract.Companion.Entrada.COLUMNA_STOCK,
            ProductosContract.Companion.Entrada.COLUMNA_CATEGORIA_PRODUCTO,
            ProductosContract.Companion.Entrada.COLUMNA_DESCRIPCION,
            ProductosContract.Companion.Entrada.COLUMNA_IMAGEN_URI
        )
        val selection = "${ProductosContract.Companion.Entrada.COLUMNA_CATEGORIA_PRODUCTO} = ?"
        val selectionArgs = arrayOf(categoryId.toString())

        val c: Cursor = db.query(
            ProductosContract.Companion.Entrada.NOMBRE_TABLA,
            columnas,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        while (c.moveToNext()) {
            items.add(
                Producto(
                    c.getInt(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_NOMBRE)),
                    c.getDouble(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_PRECIO)),
                    c.getInt(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_STOCK)),
                    c.getInt(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_CATEGORIA_PRODUCTO)),
                    c.getString(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_DESCRIPCION)),
                    c.getString(c.getColumnIndexOrThrow(ProductosContract.Companion.Entrada.COLUMNA_IMAGEN_URI))
                )
            )
        }
        c.close()
        return items
    }

    fun hasProducts(): Boolean {
        val db = helper?.readableDatabase
        val cursor = db?.query(
            ProductosContract.Companion.Entrada.NOMBRE_TABLA,
            arrayOf(ProductosContract.Companion.Entrada.COLUMNA_ID),
            null,
            null,
            null,
            null,
            null
        )
        val hasProducts = cursor?.count ?: 0 > 0
        cursor?.close()
        return hasProducts
    }

    fun deleteProduct(item: Producto){
        val db: SQLiteDatabase = helper?.writableDatabase!!
        db.delete(
            ProductosContract.Companion.Entrada.NOMBRE_TABLA,
            "idProducto = ?",
            arrayOf(item.idProducto.toString())
        )
    }

    fun updateProduct(item:Producto){
        val db: SQLiteDatabase = helper?.writableDatabase!!

        val values = ContentValues()
        values.put(ProductosContract.Companion.Entrada.COLUMNA_NOMBRE, item.nombreProducto)
        values.put(ProductosContract.Companion.Entrada.COLUMNA_PRECIO, item.precioProducto)
        values.put(ProductosContract.Companion.Entrada.COLUMNA_STOCK, item.stockProducto)
        values.put(ProductosContract.Companion.Entrada.COLUMNA_CATEGORIA_PRODUCTO, item.categoryProducto)
        values.put(ProductosContract.Companion.Entrada.COLUMNA_DESCRIPCION, item.descripcionProducto)
        values.put(ProductosContract.Companion.Entrada.COLUMNA_IMAGEN_URI, item.imageUri)

        db.update(
            ProductosContract.Companion.Entrada.NOMBRE_TABLA,
            values,
            "idProducto = ?",
            arrayOf(item.idProducto.toString())
        )
    }

    fun deleteProductsByCategoryId(categoryId: Int) {
        val db: SQLiteDatabase = helper?.writableDatabase ?: return

        val whereClause = "${ProductosContract.Companion.Entrada.COLUMNA_CATEGORIA_PRODUCTO} = ?"
        val whereArgs = arrayOf(categoryId.toString())

        db.delete(
            ProductosContract.Companion.Entrada.NOMBRE_TABLA,
            whereClause,
            whereArgs
        )
    }

}
package com.yostin.cafetin.Esquema.Crud

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.yostin.cafetin.Entity.Category
import com.yostin.cafetin.Esquema.CategoryContract
import com.yostin.cafetin.Esquema.Helper.DataBaseHelper

class CategoryCRUD(context: Context) {
    private var helper: DataBaseHelper? = null

    init {
        helper = DataBaseHelper(context)
    }

    fun newCategory(item: Category) {
        val db: SQLiteDatabase = helper?.writableDatabase!!
        val values = ContentValues()
        values.put(CategoryContract.Companion.Entrada.COLUMNA_NOMBRE, item.nombreCategory)
        values.put(CategoryContract.Companion.Entrada.COLUMNA_IMAGEN_URI, item.imageUri)
        val newRowId = db.insert(CategoryContract.Companion.Entrada.NOMBRE_TABLA, null, values)
        db.close()
    }

    fun updateCategory(item:Category){
        val db: SQLiteDatabase = helper?.writableDatabase!!

        val values = ContentValues()
        values.put(CategoryContract.Companion.Entrada.COLUMNA_NOMBRE, item.nombreCategory)
        values.put(CategoryContract.Companion.Entrada.COLUMNA_IMAGEN_URI, item.imageUri)

        db.update(
            CategoryContract.Companion.Entrada.NOMBRE_TABLA,
            values,
            "idCategory = ?",
            arrayOf(item.idCategory.toString())
        )
    }

    fun deleteCategory(item:Category){
        val db: SQLiteDatabase = helper?.writableDatabase!!
        db.delete(CategoryContract.Companion.Entrada.NOMBRE_TABLA,
            "idCategory = ?",
            arrayOf(item.idCategory.toString())
        )
    }

    fun getCategories(): ArrayList<Category> {
        val items: ArrayList<Category> = ArrayList()
        val db: SQLiteDatabase = helper?.readableDatabase!!
        val columnas = arrayOf(
            CategoryContract.Companion.Entrada.COLUMNA_ID,
            CategoryContract.Companion.Entrada.COLUMNA_NOMBRE,
            CategoryContract.Companion.Entrada.COLUMNA_IMAGEN_URI // Agregar la columna de la URI de la imagen
        )
        val c: Cursor = db.query(
            CategoryContract.Companion.Entrada.NOMBRE_TABLA,
            columnas,
            null,
            null,
            null,
            null,
            null
        )
        while (c.moveToNext()) {
            items.add(
                Category(
                    c.getInt(c.getColumnIndexOrThrow(CategoryContract.Companion.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(CategoryContract.Companion.Entrada.COLUMNA_NOMBRE)),
                    c.getString(c.getColumnIndexOrThrow(CategoryContract.Companion.Entrada.COLUMNA_IMAGEN_URI)) // Obtener la URI de la imagen
                )
            )
        }
        c.close()
        return items
    }

    fun getCategoryById(idCategory: Int): Category {
        var item: Category? = null
        val db: SQLiteDatabase = helper?.readableDatabase!!
        val columnas = arrayOf(
            CategoryContract.Companion.Entrada.COLUMNA_ID,
            CategoryContract.Companion.Entrada.COLUMNA_NOMBRE,
            CategoryContract.Companion.Entrada.COLUMNA_IMAGEN_URI
        )

        val selection = "${CategoryContract.Companion.Entrada.COLUMNA_ID} = ?"
        val selectionArgs = arrayOf(idCategory.toString())
        val c: Cursor = db.query(
            CategoryContract.Companion.Entrada.NOMBRE_TABLA,
            columnas,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        while (c.moveToNext()) {
            item = Category(
                c.getInt(c.getColumnIndexOrThrow(CategoryContract.Companion.Entrada.COLUMNA_ID)),
                c.getString(c.getColumnIndexOrThrow(CategoryContract.Companion.Entrada.COLUMNA_NOMBRE)),
                c.getString(c.getColumnIndexOrThrow(CategoryContract.Companion.Entrada.COLUMNA_IMAGEN_URI))
            )
        }
        c.close()
        return item ?: Category(0, "", "")
    }
    fun hasCategories(): Boolean {
        val db = helper?.readableDatabase
        val cursor = db?.query(
            CategoryContract.Companion.Entrada.NOMBRE_TABLA,
            arrayOf(CategoryContract.Companion.Entrada.COLUMNA_ID),
            null,
            null,
            null,
            null,
            null
        )
        val hasCategories = cursor?.count ?: 0 > 0
        cursor?.close()
        return hasCategories
    }
}
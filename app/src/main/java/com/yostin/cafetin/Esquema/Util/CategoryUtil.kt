package com.yostin.cafetin.Esquema.Util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.yostin.cafetin.Entity.Category
import com.yostin.cafetin.Esquema.CategoryContract
import com.yostin.cafetin.R

object CategoryUtil {

    fun insertDefaultCategories(context: Context, db: SQLiteDatabase?) {
        val categoryList = mutableListOf<Category>()

        // Obtener las URIs de las imágenes desde los recursos
        val imageUri1 = getResourceUri(context, R.drawable.hamburguesa)
        val imageUri2 = getResourceUri(context, R.drawable.combo_img)
        val imageUri3 = getResourceUri(context, R.drawable.bebidas)
        val imageUri4 = getResourceUri(context, R.drawable.postres)

        // Crear instancias de las categorías con las URIs de las imágenes
        categoryList.add(Category(1, "Hamburguesa", imageUri1.toString()))
        categoryList.add(Category(2, "Combo", imageUri2.toString()))
        categoryList.add(Category(3, "Bebidas", imageUri3.toString()))
        categoryList.add(Category(4, "Postres", imageUri4.toString()))

        // Insertar las categorías en la base de datos
        categoryList.forEach { category ->
            val values = ContentValues().apply {
                put(CategoryContract.Companion.Entrada.COLUMNA_NOMBRE, category.nombreCategory)
                put(CategoryContract.Companion.Entrada.COLUMNA_IMAGEN_URI, category.imageUri)
            }
            db?.insert(CategoryContract.Companion.Entrada.NOMBRE_TABLA, null, values)
        }
    }

    private fun getResourceUri(context: Context, resourceId: Int): Uri {
        return Uri.parse("android.resource://${context.packageName}/$resourceId")
    }
}

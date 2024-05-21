package com.yostin.cafetin.Esquema.Util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.yostin.cafetin.Entity.Producto
import com.yostin.cafetin.Esquema.ProductosContract
import com.yostin.cafetin.R

object ProductUtil {

    fun insertDefaultProducts(context: Context, db: SQLiteDatabase?) {
        val productList = mutableListOf<Producto>()

        // Obtener las URIs para productos de la categoría bebidas
        val bebida01 = getResourceUri(context, R.drawable.bebida01)
        val bebida02 = getResourceUri(context, R.drawable.bebida02)
        val bebida03 = getResourceUri(context, R.drawable.bebida03)

        // Obtener las URIs para productos de la categoría combos
        val combo01 = getResourceUri(context, R.drawable.combo01)
        val combo02 = getResourceUri(context, R.drawable.combo02)
        val combo03 = getResourceUri(context, R.drawable.combo03)

        // Obtener las URIs para productos de la categoría hamburguesas
        val hamburguesas01 = getResourceUri(context, R.drawable.hamburguesa01)
        val hamburguesas02 = getResourceUri(context, R.drawable.hamburguesa02)
        val hamburguesas03 = getResourceUri(context, R.drawable.hamburguesa03)

        // Obtener las URIs para productos de la categoría postres
        val postres01 = getResourceUri(context, R.drawable.postre01)
        val postres02 = getResourceUri(context, R.drawable.postre02)
        val postres03 = getResourceUri(context, R.drawable.postre03)

        // insertar datos en productos bebidas
        productList.add(Producto(1, "Coca-Cola 600ml", 3.00, 20, 3, "Una refrescante botella de 600ml", bebida01.toString()))
        productList.add(Producto(2, "Chicha Morada 500ml", 2.00, 10, 3, "Un vaso de chicha morada casero para tu disfrute personal", bebida02.toString()))
        productList.add(Producto(3, "Juego de Naranja 500ml", 1.50, 10, 3, "Un vaso de jugo de naranja natural a tu gusto", bebida03.toString()))

        // insertar datos en productos combos
        productList.add(Producto(4, "Combo Trilcito ", 12.50, 20, 2, "Disfruta nuestro combo, Hamburguesa + Coca-Cola + papas fritas", combo01.toString()))
        productList.add(Producto(5, "Combo Express", 6.50, 10, 2, "Un rico vaso de jugo de naranja + un sandwich de queso y jamon", combo02.toString()))
        productList.add(Producto(6, "Como Nuget Fresh", 9.50, 10, 2, "Deliciosos nugets de pollo + papas fritas + un fanta helada", combo03.toString()))

        // insertar datos en productos hamburguesas
        productList.add(Producto(7, "Hamburguesa Americana", 10.50, 20, 1, "Una rica hamburguesa americana", hamburguesas01.toString()))
        productList.add(Producto(8, "Hamburguesa Ranchera", 7.50, 10, 1, "Una riquisima hamburguesa, con harto chile jalapeño", hamburguesas02.toString()))
        productList.add(Producto(9, "Hamburguesa Big Trilce", 11.50, 10, 1, "La especialidad de la casa, una hamburguesa hecha con ingregientes frescos", hamburguesas03.toString()))

        // insertar datos en productos postres
        productList.add(Producto(10, "Arroz con leche", 3.50, 20, 4, "Un rico arroz con leche hecho en casa", postres01.toString()))
        productList.add(Producto(11, "Rebanada de chocolate", 4.50, 10, 4, "Una deliciosa debanada de pastel de chocolate con un fresa de acompañamiento", postres02.toString()))
        productList.add(Producto(12, "Pie de Manzana", 2.50, 10, 4, "Un pie de manzama como la abuela lo hacia, con una bola de helado", postres03.toString()))

        productList.forEach { product ->
            val values = ContentValues().apply {
                put(ProductosContract.Companion.Entrada.COLUMNA_NOMBRE, product.nombreProducto)
                put(ProductosContract.Companion.Entrada.COLUMNA_PRECIO, product.precioProducto)
                put(ProductosContract.Companion.Entrada.COLUMNA_STOCK, product.stockProducto)
                put(ProductosContract.Companion.Entrada.COLUMNA_CATEGORIA_PRODUCTO, product.categoryProducto)
                put(ProductosContract.Companion.Entrada.COLUMNA_DESCRIPCION, product.descripcionProducto)
                put(ProductosContract.Companion.Entrada.COLUMNA_IMAGEN_URI, product.imageUri)
            }
            db?.insert(ProductosContract.Companion.Entrada.NOMBRE_TABLA, null, values)
        }
    }

    private fun getResourceUri(context: Context, resourceId: Int): Uri {
        return Uri.parse("android.resource://${context.packageName}/$resourceId")
    }
}

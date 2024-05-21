package com.yostin.cafetin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yostin.cafetin.Entity.Producto
import com.yostin.cafetin.User.CategoryF

class MainActivity : AppCompatActivity(), DetalleProduct.OnAddToCartListener {

    // Lista de productos en espera
    private val productosEnEspera: MutableList<Producto> = mutableListOf()
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userId = intent.getIntExtra("userId", -1)
        Log.d("ID_MAIN", "IDMAIN: " + userId)
        asignarReference(userId)



        replaceFragment(CategoryF(), "category_fragment_tag")
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    private fun asignarReference( userId:Int) {
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navCategories -> {
                    replaceFragment(CategoryF(), "category_fragment_tag")
                    true
                }
                R.id.navCart -> {
                    // Crear un nuevo fragmento del carrito y mostrarlo
                    val cartFragment = Cart.newInstance(productosEnEspera, userId.toString())
                    replaceFragment(cartFragment, "cart_fragment_tag")
                    true
                }
                R.id.navUser -> {
                    replaceFragment(UserPerfil.newInstance(userId), "user_fragment_tag")
                    true
                }
                else -> false
            }
        }
    }

    override fun onAddToCart(nombre: String, imagen: String, currentStock: Int, precio: Double) {
        // Verificar si el producto ya está en la lista de productos en espera
        val productoExistente = productosEnEspera.find { it.nombreProducto == nombre }
        if (productoExistente != null) {
            // El producto ya está en la lista, puedes mostrar un mensaje al usuario
            Toast.makeText(this, "El producto ya está en el carrito", Toast.LENGTH_SHORT).show()
        } else {
            // El producto no está en la lista, agregarlo
            val producto = Producto(nombre, imagen, currentStock, precio)
            productosEnEspera.add(producto)
            // Mostrar un mensaje indicando que el producto fue agregado al carrito
            Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }

}





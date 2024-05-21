package com.yostin.cafetin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yostin.cafetin.Data.CartData
import com.yostin.cafetin.Entity.Detalle
import com.yostin.cafetin.Entity.Pedido
import com.yostin.cafetin.Entity.Producto
import com.yostin.cafetin.Esquema.Crud.PedidoCRUD
import com.yostin.cafetin.RecyclerViews.RecyclerViewCartAdapter
import com.yostin.cafetin.User.MetodoPago
import com.yostin.cafetin.User.MisPedidos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Cart : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewCartAdapter
    private lateinit var txtMontoPagar: TextView
    private lateinit var userId: String

    // Lista de productos en espera
    private var productosEnEspera: MutableList<Producto> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        Log.d("USER_ID_CART", "CART: " + userId)
        recyclerView = view.findViewById(R.id.rvCarrito)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RecyclerViewCartAdapter(
            productosEnEspera.convertirProductosACartData(),
            this
        )
        recyclerView.adapter = adapter

        txtMontoPagar = view.findViewById(R.id.txtMontoPagar)
        actualizarTotal()

        val btnPago = view.findViewById<Button>(R.id.btnPagar)
        btnPago.setOnClickListener {
            realizarPedido()

        }

        return view
    }

    fun actualizarTotal() {
        val total = adapter.calcularTotal()
        txtMontoPagar.text = total.toString()
    }
    // FALTA EL ID DEL USER
    fun realizarPedido() {

        val estado = "Pendiente"
        val fechaActual = SimpleDateFormat("yyyy-MM-dd").format(Date())


        val pedido = Pedido(
            idPedido = null,
            userPedido = userId.toInt(),
            fechaRegistro = fechaActual,
            estadoPedido = estado,
            monto = adapter.calcularTotal()
        )
        val detallesPedido = adapter.listCartData.map { cartData ->
            Detalle(
                idDetalle = null,
                pedidoDetalle = null,
                nameProductoPedido = cartData.productName,
                productQuantityPedido = cartData.productQuantity
            )
        }

        CoroutineScope(Dispatchers.Main).launch {
            val pedidoCRUD = PedidoCRUD(requireContext())
            pedidoCRUD.insertarPedidoConDetalles(pedido, detallesPedido)

            // Limpiamos el carrito después de realizar el pedido
            productosEnEspera.clear()
            adapter.notifyDataSetChanged()
            actualizarTotal()
        }
    }

    private fun List<Producto>.convertirProductosACartData(): MutableList<CartData> {
        return map { producto ->
            CartData(
                productName = producto.nombreProducto ?: "",
                productPrice = producto.precioProducto ?: 0.0,
                productQuantity = producto.stockProducto ?: 0,
                productImageUri = producto.imageUri ?: ""
            )
        }.toMutableList()
    }

    companion object {
        @JvmStatic
        fun newInstance(productosEnEspera: MutableList<Producto>, userId: String) =
            Cart().apply {
                this.productosEnEspera = productosEnEspera
                this.userId = userId
            }
    }
}
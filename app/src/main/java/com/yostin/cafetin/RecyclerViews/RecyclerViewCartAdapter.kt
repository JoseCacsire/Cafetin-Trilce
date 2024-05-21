package com.yostin.cafetin.RecyclerViews

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yostin.cafetin.Cart
import com.yostin.cafetin.Data.CartData
import com.yostin.cafetin.R


class RecyclerViewCartAdapter(
    val listCartData: MutableList<CartData>,
    private val cartFragment: Cart) :
    RecyclerView.Adapter<RecyclerViewCartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtCartProductName)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPriceCartProduct)
        val txtCantidad: TextView = itemView.findViewById(R.id.txtCartCantidad)
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProductCart)
        val btnQuitarProducto: ImageButton = itemView.findViewById(R.id.btnQuitarProducto)
        val btnAumentarCart: ImageButton = itemView.findViewById(R.id.btnAumentarCart)
        val btnDisminuirCart: ImageButton = itemView.findViewById(R.id.btnDisminuirCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fila_carrito, parent, false)
        Log.d("CartData", "data: " + listCartData)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = listCartData[position]

        holder.txtName.text = currentItem.productName
        holder.txtCantidad.text = currentItem.productQuantity.toString()

        // Calcular el precio total
        val totalPrice = currentItem.productPrice * currentItem.productQuantity
        holder.txtPrice.text = totalPrice.toString()

        // Cargar la imagen utilizando Glide
        Glide.with(holder.itemView)
            .load(currentItem.productImageUri)
            .placeholder(R.drawable.add)
            .error(R.drawable.add)
            .into(holder.imgProduct)

        holder.btnQuitarProducto.setOnClickListener {
            listCartData.removeAt(position)
            notifyDataSetChanged()
            cartFragment.actualizarTotal() // Actualizar total al quitar producto
        }

        holder.btnAumentarCart.setOnClickListener {

            currentItem.productQuantity++
            notifyItemChanged(position)
            cartFragment.actualizarTotal() // Actualizar total al aumentar cantidad
        }

        holder.btnDisminuirCart.setOnClickListener {
            if (currentItem.productQuantity > 1) {
                currentItem.productQuantity--
                notifyItemChanged(position)
                cartFragment.actualizarTotal() // Actualizar total al disminuir cantidad
            }
        }
    }


    override fun getItemCount() = listCartData.size

    fun calcularTotal(): Double {
        var total = 0.0
        for (item in listCartData) {
            total += item.productPrice * item.productQuantity
        }
        return total
    }

}





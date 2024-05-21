package com.yostin.cafetin.RecyclerViews

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yostin.cafetin.DetalleProduct
import com.yostin.cafetin.Entity.Producto
import com.yostin.cafetin.R

class RecyclerViewProductsAdapter(
    private val getActivity: Context,
    private val productList: MutableList<Producto>
) : RecyclerView.Adapter<RecyclerViewProductsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fila_products_catalog, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val producto = productList[position]


        holder.tvProductName.text = producto.nombreProducto.toString()
        holder.tvStockProduct.text = producto.stockProducto.toString()
        holder.tvPriceProduct.text = producto.precioProducto?.toString() ?: ""
        producto.imageUri?.let { uri ->
            Glide.with(getActivity)
                .load(uri)
                .placeholder(R.drawable.combo)
                .error(R.drawable.combo)
                .into(holder.tvProductImg)
        }
        holder.ivButtonDetail.setOnClickListener {
            val detailFragment = DetalleProduct()
            val bundle = Bundle().apply {
                putInt("productoId", producto.idProducto.toString().toInt())
                putString("nombreProducto", producto.nombreProducto)
                putDouble("precioProducto", producto.precioProducto ?: 0.0)
                putInt("stockProducto", producto.stockProducto ?: 0)
                putString("descripcionProducto", producto.descripcionProducto)
                putString("imagenProductoUri", producto.imageUri)
            }

            Log.d("RecyclerViewAdapter", "URI de la imagen: ${producto.imageUri}") // Registro de Log agregado aquí

            detailFragment.arguments = bundle
            val fragmentTransaction = (getActivity as AppCompatActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_products, detailFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


    }
    override fun getItemCount(): Int {
        return productList.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProductName: TextView = itemView.findViewById(R.id.txtNameProduct)
        val tvProductImg: ImageView = itemView.findViewById(R.id.imageView3)
        val tvStockProduct: TextView = itemView.findViewById(R.id.txtStockProduct)
        val tvPriceProduct: TextView = itemView.findViewById(R.id.txtPriceProduct)
        val ivButtonDetail: ImageView = itemView.findViewById(R.id.ivDetail)
    }
}
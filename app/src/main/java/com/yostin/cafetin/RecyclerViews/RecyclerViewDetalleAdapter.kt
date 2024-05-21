package com.yostin.cafetin.RecyclerViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yostin.cafetin.Entity.Detalle
import com.yostin.cafetin.R

class RecyclerViewDetalleAdapter(
    private val context: Context,
    private val detalleList: MutableList<Detalle>
) : RecyclerView.Adapter<RecyclerViewDetalleAdapter.DetalleHolder>() {

    class DetalleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreDetalleProducto : TextView = itemView.findViewById(R.id.txtProductoPedido)
        val cantidadDetalleProducto : TextView = itemView.findViewById(R.id.txtCantidadPedido)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.fila_ticket_pedido, parent, false)
        return DetalleHolder(itemView)
    }

    override fun onBindViewHolder(holder: DetalleHolder, position: Int) {
        val detalle = detalleList[position]

        holder.nombreDetalleProducto.text = detalle.nameProductoPedido
        holder.cantidadDetalleProducto.text = detalle.productQuantityPedido.toString()
    }

    override fun getItemCount(): Int {
        return detalleList.size
    }
}
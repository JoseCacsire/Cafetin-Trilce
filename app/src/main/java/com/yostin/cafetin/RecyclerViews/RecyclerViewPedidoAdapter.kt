package com.yostin.cafetin.RecyclerViews

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yostin.cafetin.Entity.Pedido
import com.yostin.cafetin.R
import com.yostin.cafetin.User.DetalleCreditoPago

class RecyclerViewPedidoAdapter (
    private val getActivity: Context,
    val pedidoList: MutableList<Pair<Pedido, String>>
): RecyclerView.Adapter<RecyclerViewPedidoAdapter.MyPedidoHolder>() {

    class MyPedidoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreCliente: TextView = itemView.findViewById(R.id.txtClientePedido)
        val estadoPedido : TextView = itemView.findViewById(R.id.txtEstadoPedido)
        val montoPedido : TextView = itemView.findViewById(R.id.txtPedidoTotal)
        val fechaPedido : TextView = itemView.findViewById(R.id.txtPedidoFecha)
        val PedidoId : TextView = itemView.findViewById(R.id.txtPedidoId)
        val detallePedido : TextView = itemView.findViewById(R.id.txtPedidoDetalle)
    }

    override fun getItemCount(): Int {
        return pedidoList.size
    }

    override fun onBindViewHolder(holder: MyPedidoHolder, position: Int) {
        val (pedido, nombreCliente) = pedidoList[position]

        // Asignar los datos del pedido a los TextViews
        holder.nombreCliente.text = nombreCliente
        holder.estadoPedido.text = pedido.estadoPedido
        holder.montoPedido.text = pedido.monto.toString()
        holder.fechaPedido.text = pedido.fechaRegistro
        holder.PedidoId.text = pedido.idPedido.toString()
        holder.detallePedido.setOnClickListener {
            val idPedido = pedidoList[position].first.idPedido
            val estado = pedidoList[position].first.estadoPedido
            val fecha = pedidoList[position].first.fechaRegistro
            val monto = pedidoList[position].first.monto // Se obtiene el monto del pedido

            Log.d("MONTO", "Datos Recuperados: ${pedido.monto}")

            val intent = Intent(getActivity, DetalleCreditoPago::class.java)
            intent.putExtra("idPedido", idPedido)
            intent.putExtra("nombreCliente", nombreCliente)
            intent.putExtra("estado", estado)
            intent.putExtra("fecha", fecha)
            intent.putExtra("monto", monto) // Se pasa el monto del pedido al detalle del pedido
            getActivity.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPedidoHolder {
        val itemView = LayoutInflater.from(getActivity).inflate(R.layout.fila_pedidos_user, parent, false)
        return MyPedidoHolder(itemView)
    }
}



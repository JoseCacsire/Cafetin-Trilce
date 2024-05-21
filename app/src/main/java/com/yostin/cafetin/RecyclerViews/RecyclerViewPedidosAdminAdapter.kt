package com.yostin.cafetin.RecyclerViews

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.yostin.cafetin.Admin.CreditoPagoAdmin
import com.yostin.cafetin.Entity.Pedido
import com.yostin.cafetin.Esquema.Crud.CategoryCRUD
import com.yostin.cafetin.Esquema.Crud.PedidoCRUD
import com.yostin.cafetin.Esquema.Crud.ProductoCRUD
import com.yostin.cafetin.R

class RecyclerViewPedidosAdminAdapter (
    private val getActivity: Context,
    val pedidoList: MutableList<Pair<Pedido, String>>
): RecyclerView.Adapter<RecyclerViewPedidosAdminAdapter.MyPedidoHolder>() {

    class MyPedidoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreCliente: TextView = itemView.findViewById(R.id.txtUserPedido)
        val PedidoId : TextView = itemView.findViewById(R.id.textoPedido)
        val estadoPedido : TextView = itemView.findViewById(R.id.txtEstadoPedido)
        val montoPedido : TextView = itemView.findViewById(R.id.txtMontoPedido)
        val fechaPedido : TextView = itemView.findViewById(R.id.txtFechaPedido)
        val btnActualizarEstado : ImageView = itemView.findViewById(R.id.btnActualizarEstado)
        val btnPedidoDetalleAdmin : ImageView = itemView.findViewById(R.id.btnDetallePedidoAdmin)
    }

    override fun getItemCount(): Int {
        return pedidoList.size
    }

    override fun onBindViewHolder(holder: MyPedidoHolder, position: Int) {
        val (pedido, nombreCliente) = pedidoList[position]

        // Asignar los datos del pedido a los TextViews
        holder.nombreCliente.text = nombreCliente
        holder.montoPedido.text = pedido.monto.toString()
        holder.fechaPedido.text = pedido.fechaRegistro
        holder.PedidoId.text = pedido.idPedido.toString()
        holder.estadoPedido.text = pedido.estadoPedido.toString()
        holder.btnActualizarEstado.setOnClickListener {
            val editarBuilder = AlertDialog.Builder(getActivity)
            editarBuilder.setTitle("¿Desea Editar?")
            editarBuilder.setMessage("¿Desea actualizar el estado del producto?")
            editarBuilder.setPositiveButton("Sí") { dialog, which ->
                val builder = AlertDialog.Builder(getActivity)
                builder.setTitle("Actualizar Estado")
                builder.setMessage("Elije el nuevo estado del producto:")
                builder.setPositiveButton("Pendiente") { dialog, which ->
                    actualizarEstadoPedido(pedido, "Pendiente")
                }
                builder.setNeutralButton("Pagado") { dialog, which ->
                    actualizarEstadoPedido(pedido, "Pagado")
                }
                builder.setNegativeButton("Enviado") { dialog, which ->
                    actualizarEstadoPedido(pedido, "Enviado")
                }
                builder.show()
            }
            editarBuilder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            editarBuilder.show()
        }

        holder.btnPedidoDetalleAdmin.setOnClickListener {

            val estado = pedidoList[position].first.estadoPedido
            val fecha = pedidoList[position].first.fechaRegistro
            val monto = pedidoList[position].first.monto
            val id = pedidoList[position].first.idPedido

            val intent = Intent(getActivity, CreditoPagoAdmin::class.java)

            intent.putExtra("estadoDetalle", estado)
            intent.putExtra("fechaDetalle", fecha)
            intent.putExtra("montoPedido", monto)
            intent.putExtra("idPedido", id)
            intent.putExtra("nombreCliente", nombreCliente)

            getActivity.startActivity(intent)

        }

        // Agregar un log para verificar si se recupera al menos un dato
        Log.d("RecyclerViewPedidosAdminAdapter", "Datos del pedidoAdmin en posición $position: $nombreCliente, Estado: ${pedido.estadoPedido}, Monto: ${pedido.monto}, Fecha: ${pedido.fechaRegistro}, ID: ${pedido.idPedido}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPedidoHolder {
        val itemView = LayoutInflater.from(getActivity).inflate(R.layout.fila_historial_pedido, parent, false)
        return MyPedidoHolder(itemView)
    }

    private fun actualizarEstadoPedido(pedido: Pedido, nuevoEstado: String) {
        val pedidoCRUD = PedidoCRUD(getActivity)
        pedidoCRUD.actualizarEstadoPedidoPorId(pedido.idPedido, nuevoEstado)
        pedido.estadoPedido = nuevoEstado // Actualizar el estado del pedido en la lista de pedidos
        notifyDataSetChanged() // Notificar al RecyclerView que los datos han cambiado
    }
}

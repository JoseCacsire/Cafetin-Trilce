package com.yostin.cafetin.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yostin.cafetin.Entity.Detalle
import com.yostin.cafetin.Esquema.Crud.PedidoCRUD
import com.yostin.cafetin.R
import com.yostin.cafetin.RecyclerViews.RecyclerViewDetalleAdapter
import com.yostin.cafetin.databinding.ActivityDetalleCreditoPagoBinding
import kotlinx.coroutines.launch

class DetalleCreditoPago : AppCompatActivity() {
    private lateinit var detalle: MutableList<Detalle>
    private lateinit var binding: ActivityDetalleCreditoPagoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleCreditoPagoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idPedido = intent.getIntExtra("idPedido", -1)
        val nombreCliente = intent.getStringExtra("nombreCliente")
        val estado = intent.getStringExtra("estado")
        val fecha = intent.getStringExtra("fecha")
        val monto = intent.getDoubleExtra("monto", 0.0)

        binding.txtComprador.text = nombreCliente
        binding.txtEstadoDelPedido.text = estado
        binding.txtFecha.text = fecha
        binding.txtDetalleTotal.text = monto.toString()

        recuperarDetalle(idPedido)
        retroceder()
    }

    private fun recuperarDetalle(idPedido: Int) {
        lifecycleScope.launch {
            val detalleCRUD = PedidoCRUD(this@DetalleCreditoPago)
            val detallesRecuperados = detalleCRUD.recuperarDetallesPorIdPedido(idPedido)
            detalle = detallesRecuperados.toMutableList()
            crearYAsignarAdaptador()
        }
    }

    private fun crearYAsignarAdaptador() {
        val adapter = RecyclerViewDetalleAdapter(this, detalle)
        binding.rvDetalleProduct.adapter = adapter
        binding.rvDetalleProduct.layoutManager = LinearLayoutManager(this)
    }

    private fun retroceder(){
        binding.btnBackDetail.setOnClickListener{
            onBackPressed()
        }
    }
}
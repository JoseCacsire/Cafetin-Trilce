package com.yostin.cafetin.Admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yostin.cafetin.Entity.Detalle
import com.yostin.cafetin.Esquema.Crud.PedidoCRUD
import com.yostin.cafetin.R
import com.yostin.cafetin.RecyclerViews.RecyclerViewDetalleAdapter
import com.yostin.cafetin.databinding.ActivityCreditoPagoAdminBinding
import com.yostin.cafetin.databinding.ActivityDetalleCreditoPagoBinding
import kotlinx.coroutines.launch

class CreditoPagoAdmin : AppCompatActivity() {
    private lateinit var detalle: MutableList<Detalle>
    private lateinit var binding: ActivityCreditoPagoAdminBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreditoPagoAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idPedido = intent.getIntExtra("idPedido", -1)
        val nombreCliente = intent.getStringExtra("nombreCliente")
        val estado = intent.getStringExtra("estadoDetalle")
        val fecha = intent.getStringExtra("fechaDetalle")
        val monto = intent.getDoubleExtra("montoPedido", 0.0)

        binding.txtComprador.text = nombreCliente
        binding.txtEstadoDelPedido.text = estado
        binding.txtFecha.text = fecha
        binding.txtDetalleTotal.text = monto.toString()

        recuperarDetalle(idPedido)
        retroceder()
    }

    private fun recuperarDetalle(idPedido: Int) {
        lifecycleScope.launch {
            val detalleCRUD = PedidoCRUD(this@CreditoPagoAdmin)
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
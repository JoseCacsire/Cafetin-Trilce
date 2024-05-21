package com.yostin.cafetin.User

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yostin.cafetin.Entity.Pedido
import com.yostin.cafetin.Esquema.Crud.PedidoCRUD
import com.yostin.cafetin.RecyclerViews.RecyclerViewPedidoAdapter
import com.yostin.cafetin.databinding.ActivityMisPedidosBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log


class MisPedidos : AppCompatActivity() {
    private lateinit var binding: ActivityMisPedidosBinding
    private lateinit var adapter: RecyclerViewPedidoAdapter
    private lateinit var pedidos: MutableList<Pair<Pedido, String>> // Variable global para almacenar los pedidos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMisPedidosBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Obtener el userId de los extras del Intent
        val userId = intent.getIntExtra("userId", -1)
        // Cargar los pedidos asociados al userId
        recuperarPedidos(userId)
        retroceder()
    }

    private fun recuperarPedidos(userId: Int) {
        lifecycleScope.launch {
            val pedidoCRUD = PedidoCRUD(this@MisPedidos)
            val pedidosRecuperados = pedidoCRUD.listarPedidosConNombreUsuarioPorIdUsuario(userId)

            // Actualizar la variable global con los pedidos obtenidos
            pedidos = pedidosRecuperados.toMutableList()

            Log.d("DATOS RECUPERADOS", "Datos Recuperados: $pedidos")

            // Crear el adaptador y asignarlo al RecyclerView después de actualizar los datos
            crearYAsignarAdaptador()
        }
    }

    private fun retroceder(){
        binding.btnAtrasPerfil.setOnClickListener {
            onBackPressed()
        }
    }

    private fun crearYAsignarAdaptador() {
        // Obtener una referencia al RecyclerView
        val recyclerView = binding.rvMisPedidos

        // Configurar un LinearLayoutManager para el RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Crear el adaptador y asignarlo al RecyclerView
        adapter = RecyclerViewPedidoAdapter(this, pedidos)
        recyclerView.adapter = adapter

    }
}












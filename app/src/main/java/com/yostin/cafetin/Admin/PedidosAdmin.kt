package com.yostin.cafetin.Admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yostin.cafetin.Entity.Pedido
import com.yostin.cafetin.Esquema.Crud.PedidoCRUD
import com.yostin.cafetin.R
import com.yostin.cafetin.RecyclerViews.RecyclerViewPedidosAdminAdapter
import com.yostin.cafetin.databinding.FragmentPedidosAdminBinding
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class PedidosAdmin : Fragment() {

    private lateinit var binding: FragmentPedidosAdminBinding
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter: RecyclerViewPedidosAdminAdapter
    private lateinit var pedidos: MutableList<Pair<Pedido, String>> // Variable global para almacenar los pedidos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar y devolver el layout usando View Binding
        binding = FragmentPedidosAdminBinding.inflate(inflater, container, false)
        recuperarPedidos()
        return binding.root
    }

    private fun recuperarPedidos() {
        Log.d("PedidosAdmin", "Recuperando pedidos...") // Agregar mensaje de log

        lifecycleScope.launch {
            val pedidoCRUD = PedidoCRUD(requireContext())
            val pedidosRecuperados = pedidoCRUD.listarPedidos()

            Log.d("DATOS RECUPERADOS", "DatosRecuperados Recuperados: $pedidosRecuperados")
            // Actualizar la variable global con los pedidos obtenidos
            pedidos = pedidosRecuperados.toMutableList()
            Log.d("DATOS RECUPERADOS", "DatosAdin Recuperados: $pedidos")

            Log.d("DATOS RECUPERADOS", "DatosAdin Recuperados: $pedidos")

            // Crear el adaptador y asignarlo al RecyclerView después de actualizar los datos
            crearYAsignarAdaptador()
        }
    }

    private fun crearYAsignarAdaptador() {

        val recyclerView = binding.rvPedidosAdmin

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerViewPedidosAdminAdapter(requireContext(),pedidos)
        recyclerView.adapter = adapter

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PedidosAdmin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
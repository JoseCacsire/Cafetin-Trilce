package com.yostin.cafetin.User

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yostin.cafetin.Entity.Producto
import com.yostin.cafetin.Esquema.Crud.ProductoCRUD
import com.yostin.cafetin.R
import com.yostin.cafetin.RecyclerViews.RecyclerViewProductsAdapter

class Products : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var recyclerViewProductsAdapter: RecyclerViewProductsAdapter? = null
    private var productoList = mutableListOf<Producto>()
    private var productoCRUD: ProductoCRUD? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_products,container,false)


        // Obtener los argumentos pasados al fragmento
        val categoryName = arguments?.getString("categoryName")
        val categoryId = arguments?.getInt("categoryId")

        // Buscar el EditText y establecer el nombre de la categoría
        val inpNameProduct = view.findViewById<TextView>(R.id.txtProducto)
        categoryName?.let { inpNameProduct.setText(it) }
        productoCRUD = ProductoCRUD(requireContext())

        recyclerView = view.findViewById(R.id.rvProductList)
        recyclerViewProductsAdapter = RecyclerViewProductsAdapter(requireContext(), productoList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = recyclerViewProductsAdapter

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.apply{
                replace(R.id.fragment_category, CategoryF())
                addToBackStack(null)
                commit()
            }
        }

        loadProductos(categoryId)
        return view
    }

    private fun loadProductos(id:Int?) {
        productoList.clear()
        productoList.addAll(productoCRUD!!.getProductsByCategoryId(id.toString().toInt()))
        recyclerViewProductsAdapter?.notifyDataSetChanged()
    }
}
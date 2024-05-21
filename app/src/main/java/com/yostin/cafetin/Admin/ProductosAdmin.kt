package com.yostin.cafetin.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yostin.cafetin.Entity.Producto
import com.yostin.cafetin.Esquema.Crud.ProductoCRUD
import com.yostin.cafetin.Esquema.Helper.DataBaseHelper
import com.yostin.cafetin.Esquema.Util.ProductUtil
import com.yostin.cafetin.R
import com.yostin.cafetin.RecyclerViews.RecyclerViewProductsAdminAdapter



class ProductosAdmin : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewProductosAdminAdapter: RecyclerViewProductsAdminAdapter
    private val productoList = mutableListOf<Producto>()
    private var productoCRUD: ProductoCRUD? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {}
        val floatingActionButton = requireActivity().findViewById<FloatingActionButton>(R.id.btnAddCategoria)
        floatingActionButton.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_productos_admin, container, false)

        // Obtener los argumentos pasados al fragmento
        val categoryName = arguments?.getString("categoryName")
        val categoryId = arguments?.getInt("categoryId")

        // Buscar el EditText y establecer el nombre de la categoría
        val inpNameProduct = view.findViewById<TextView>(R.id.txtProducto)
        categoryName?.let { inpNameProduct.setText(it) }

        // Inicializar productoCRUD antes de usarlo
        productoCRUD = ProductoCRUD(requireContext())

        recyclerView = view.findViewById(R.id.rvProductosAdmin)
        recyclerViewProductosAdminAdapter = RecyclerViewProductsAdminAdapter(requireContext(), productoList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerViewProductosAdminAdapter

        if (!productoCRUD!!.hasProducts()) {
            // Si no hay productos, insertar los productos por defecto
            val dbHelper = DataBaseHelper(requireContext())
            val db = dbHelper.writableDatabase
            db?.let { ProductUtil.insertDefaultProducts(requireContext(), it) }
        }

        productoCRUD = ProductoCRUD(requireContext())
        loadProductos(categoryId)

        val btnAddProducto = view.findViewById<FloatingActionButton>(R.id.btnAddProduct)
        btnAddProducto.setOnClickListener {
            val formAddProductFragment = FormAddProduct()

            val bundleFormAddProduct = Bundle().apply {
                putInt("categoryId", categoryId?:0)
            }
            formAddProductFragment.arguments = bundleFormAddProduct

            // Abrir FormAddProduct
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_admin_producto, formAddProductFragment)
                .addToBackStack(null)
                .commit()
//            activity?.supportFragmentManager?.beginTransaction()?.apply {
//                replace(R.id.fragment_admin_producto, FormAddProduct())
//                addToBackStack(null)
//                commit()
//            }
            btnAddProducto.visibility = View.GONE
        }

        val btnBack = view.findViewById<ImageButton>(R.id.btnBackUpdateProduct)
        btnBack.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }

        return view
    }

    private fun loadProductos(id:Int?) {
        productoList.clear()
        productoList.addAll(productoCRUD!!.getProductsByCategoryId(id.toString().toInt()))
        recyclerViewProductosAdminAdapter.notifyDataSetChanged()
    }
}
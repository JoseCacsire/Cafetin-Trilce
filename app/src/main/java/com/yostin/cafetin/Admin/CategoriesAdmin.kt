package com.yostin.cafetin.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yostin.cafetin.Entity.Category
import com.yostin.cafetin.Esquema.Crud.CategoryCRUD
import com.yostin.cafetin.Esquema.Helper.DataBaseHelper
import com.yostin.cafetin.Esquema.Util.CategoryUtil
import android.content.Context
import com.yostin.cafetin.R
import com.yostin.cafetin.RecyclerViews.RecyclerViewCategoriesAdminAdapter


class CategoriesAdmin : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewCategoriesAdminAdapter: RecyclerViewCategoriesAdminAdapter
    private val categoryList = mutableListOf<Category>()
    private var categoryCRUD: CategoryCRUD? = null
    private var dbHelper: DataBaseHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories_admin, container, false)

        recyclerView = view.findViewById(R.id.rvCategoryAdmin)
        recyclerViewCategoriesAdminAdapter = RecyclerViewCategoriesAdminAdapter(requireContext(), categoryList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerViewCategoriesAdminAdapter

        categoryCRUD = CategoryCRUD(requireContext())
        dbHelper = DataBaseHelper(requireContext())

        val db = dbHelper?.writableDatabase

        // Verificar si hay categorías en la base de datos antes de insertar las categorías por defecto
        if (!categoryCRUD!!.hasCategories()) {
            db?.let { CategoryUtil.insertDefaultCategories(requireContext(), it) }
        }

        loadCategories()

        val btnAddCategory = view.findViewById<FloatingActionButton>(R.id.btnAddCategoria)
        btnAddCategory.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_admin_categoria, CategoryForm())
                addToBackStack(null)
                commit()
            }
            btnAddCategory.visibility = View.GONE
        }
        return view
    }

    private fun loadCategories() {
        categoryList.clear()
        categoryList.addAll(categoryCRUD!!.getCategories())
        recyclerViewCategoriesAdminAdapter.notifyDataSetChanged()
    }
}





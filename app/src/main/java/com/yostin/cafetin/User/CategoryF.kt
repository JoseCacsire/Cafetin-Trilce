package com.yostin.cafetin.User

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yostin.cafetin.Entity.Category
import com.yostin.cafetin.Esquema.Crud.CategoryCRUD
import com.yostin.cafetin.R
import com.yostin.cafetin.RecyclerViews.RecyclerViewCategoryAdapter


class CategoryF : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit  var recyclerViewCategoryAdapter: RecyclerViewCategoryAdapter
    private var categoryList = mutableListOf<Category>()
    private var categoryCRUD: CategoryCRUD? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)

        recyclerView = view.findViewById(R.id.rvCategoryLists)
        recyclerViewCategoryAdapter = RecyclerViewCategoryAdapter(requireContext(), categoryList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerViewCategoryAdapter
        categoryCRUD = CategoryCRUD(requireContext())
        loadCategories()
        return view
    }

    private fun loadCategories() {
        categoryList.clear()
        categoryList.addAll(categoryCRUD!!.getCategories())

        // Agrega un registro para verificar si se están obteniendo las categorías correctamente
        for (category in categoryList) {
            Log.d("CategoryData", "ID: ${category.idCategory}, Nombre: ${category.nombreCategory}, ImageUri: ${category.imageUri}")
        }

        recyclerViewCategoryAdapter.notifyDataSetChanged()
    }
}
package com.yostin.cafetin.RecyclerViews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yostin.cafetin.Admin.ActualizarCategoria
import com.yostin.cafetin.Admin.ProductosAdmin
import com.yostin.cafetin.Entity.Category
import com.yostin.cafetin.Esquema.Crud.CategoryCRUD
import com.yostin.cafetin.Esquema.Crud.ProductoCRUD
import com.yostin.cafetin.R


class RecyclerViewCategoriesAdminAdapter(
    private val getActivity: Context,
    private val categoryList: MutableList<Category>
) : RecyclerView.Adapter<RecyclerViewCategoriesAdminAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fila_categorias_admin, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = categoryList[position]

        holder.tvIdCategory.text = category.idCategory.toString()
        holder.tvCategoryTitle.text = category.nombreCategory
        category.imageUri?.let { uri ->
            Glide.with(getActivity)
                .load(uri)
                .placeholder(R.drawable.add)
                .error(R.drawable.add)
                .into(holder.ivCategoryImage)
        }

        holder.btnUpdateCategory.setOnClickListener {
            val actualizarCategoria = ActualizarCategoria()

            val bundle = Bundle().apply {

                putString("categoryName", category.nombreCategory)
                putString("imageUri", category.imageUri)
                putInt("idCategory", category.idCategory!!)
            }
            actualizarCategoria.arguments = bundle

            val fragmentTransaction = (getActivity as AppCompatActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_admin_categoria, actualizarCategoria)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        holder.btnDeleteCategory.setOnClickListener {
            val builder = AlertDialog.Builder(getActivity)
            builder.setTitle("Confirmar eliminación")
            builder.setMessage("¿Estás seguro de que deseas eliminar esta categoría?")
            builder.setPositiveButton("Sí"){ dialog, which ->
                val categoryToDelete = categoryList[position]
                val categoryCRUD = CategoryCRUD(getActivity)
                val productCRUD = ProductoCRUD(getActivity)
                productCRUD.deleteProductsByCategoryId(category.idCategory!!)
                categoryCRUD.deleteCategory(categoryToDelete)
                categoryList.removeAt(position)
                notifyDataSetChanged()
            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }
        holder.btnListProducst.setOnClickListener{
            val listaProductos = ProductosAdmin()
            // Crear un Bundle para pasar datos al fragmento
            val bundle = Bundle().apply {
                putString("categoryName", category.nombreCategory)
                putInt("categoryId", category.idCategory.toString().toInt())
            }
            // Establecer el Bundle como argumentos del fragmento
            listaProductos.arguments = bundle
            val fragmentTransaction = (getActivity as AppCompatActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_admin_categoria, listaProductos )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategoryTitle: TextView = itemView.findViewById(R.id.txtCategoriaAdmin)
        val tvIdCategory: TextView = itemView.findViewById(R.id.txtIdCategoria)
        val btnUpdateCategory: ImageButton = itemView.findViewById(R.id.ibUpdateCategoria)
        val ivCategoryImage: ImageView = itemView.findViewById(R.id.ivCategoryImage)
        val btnDeleteCategory : ImageButton = itemView.findViewById(R.id.btnBorrarCategoria)
        val btnListProducst : ImageButton = itemView.findViewById(R.id.ibListaProductos)
    }
}

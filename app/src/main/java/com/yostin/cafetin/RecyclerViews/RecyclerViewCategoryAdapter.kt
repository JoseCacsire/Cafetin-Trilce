package com.yostin.cafetin.RecyclerViews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yostin.cafetin.User.Products
import com.yostin.cafetin.R
import com.yostin.cafetin.Entity.Category


class RecyclerViewCategoryAdapter(
    private val getActivity: Context,
    private val categoryList: MutableList<Category>
):RecyclerView.Adapter<RecyclerViewCategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_category_list_item,parent,false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = categoryList[position]

//        holder.tvIdCategory = category.idCategory.toString()
        holder.tvCategoryTitle.text = category.nombreCategory
        category.imageUri?.let { uri ->
            Glide.with(getActivity)
                .load(uri)
                .placeholder(R.drawable.combo)
                .error(R.drawable.combo)
                .into(holder.ivCategoryImage)
        }
        holder.CardView.setOnClickListener {
            val productFragment = Products()
            // Crear un Bundle para pasar datos al fragmento
            val bundle = Bundle().apply {
                putString("categoryName", category.nombreCategory)
                putInt("categoryId", category.idCategory.toString().toInt())
            }
            // Establecer el Bundle como argumentos del fragmento
            productFragment.arguments = bundle
            val fragmentTransaction = (getActivity as AppCompatActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_category, productFragment )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
    override fun getItemCount(): Int {
        return categoryList.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvCategoryTitle : TextView = itemView.findViewById(R.id.tvCategoryTitle)
        val CardView : CardView = itemView.findViewById(R.id.cardView)
        val ivCategoryImage: ImageView = itemView.findViewById(R.id.ivUserCategoryImg)
    }
}
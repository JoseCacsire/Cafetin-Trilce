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
import com.yostin.cafetin.Admin.ActualizarProducto
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yostin.cafetin.Entity.Producto
import com.yostin.cafetin.Esquema.Crud.CategoryCRUD
import com.yostin.cafetin.Esquema.Crud.ProductoCRUD
import com.yostin.cafetin.R

class RecyclerViewProductsAdminAdapter(
    private val getActivity: Context,
    private val productoList: MutableList<Producto>
) : RecyclerView.Adapter<RecyclerViewProductsAdminAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fila_products, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val producto = productoList[position]


        holder.tvProductName.text = producto.nombreProducto.toString()
        holder.tvStockProduct.text = producto.stockProducto.toString()
        holder.tvPriceProduct.text = producto.precioProducto?.toString() ?: ""
        producto.imageUri?.let { uri ->
            Glide.with(getActivity)
                .load(uri)
                .placeholder(R.drawable.add)
                .error(R.drawable.add)
                .into(holder.tvProductImg)
        }

        holder.btnUpdateProduct.setOnClickListener {
            val actualizarProducto = ActualizarProducto()

            val bundle = Bundle().apply {

                putString("productName", producto.nombreProducto)
                putString("imageUri", producto.imageUri)
                putInt("idProduct", producto.idProducto!!)
                putInt("idCategoria", producto.categoryProducto!!)
                putDouble("productPrice", producto.precioProducto!!)
                putInt("productStock",producto.stockProducto!!)
                putString("productDescription", producto.descripcionProducto)
            }
            actualizarProducto.arguments = bundle

            val fragmentTransaction = (getActivity as AppCompatActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_admin_producto, actualizarProducto)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        holder.btnDeleteProduct.setOnClickListener {
            val builder = AlertDialog.Builder(getActivity)
            builder.setTitle("Confirmar eliminación")
            builder.setMessage("¿Estás seguro de que deseas eliminar este Producto?")
            builder.setPositiveButton("Sí"){ dialog, which ->
                val productToDelete = productoList[position]
                val productoCRUD = ProductoCRUD(getActivity)
                productoCRUD.deleteProduct(productToDelete)
                productoList.removeAt(position)
                notifyDataSetChanged()
            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }


    }
    override fun getItemCount(): Int {
        return productoList.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProductName: TextView = itemView.findViewById(R.id.txtNameProduct)
        val tvProductImg: ImageView = itemView.findViewById(R.id.imageView3)
        val tvStockProduct: TextView = itemView.findViewById(R.id.txtStockProduct)
        val tvPriceProduct: TextView = itemView.findViewById(R.id.txtPriceProduct)
        val btnUpdateProduct: ImageButton = itemView.findViewById(R.id.btnEditProduct)
        val btnDeleteProduct: ImageButton = itemView.findViewById(R.id.btnDeleteProduct)
//        val ivButtonDetail: ImageView = itemView.findViewById(R.id.ivDetail)
    }
}
package com.yostin.cafetin

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import android.widget.Button



class DetalleProduct : Fragment() {
    interface OnAddToCartListener {
        fun onAddToCart(nombre: String, imagen: String, currentStock: Int, precio: Double)
    }

    private var addToCartListener: OnAddToCartListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAddToCartListener) {
            addToCartListener = context
        } else {
            throw RuntimeException("$context debe implementar OnAddToCartListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalle_product, container, false)

        // Recuperar los datos del Bundle
        val productName = arguments?.getString("nombreProducto") ?: ""
        val productPrice = arguments?.getDouble("precioProducto") ?: 0.0
        val productStock = arguments?.getInt("stockProducto") ?: 0
        val productDescription = arguments?.getString("descripcionProducto") ?: ""
        val productImageUri = arguments?.getString("imagenProductoUri") ?: ""

        val productNameTextView = view.findViewById<TextView>(R.id.txtProductDetailName)
        productNameTextView.text = productName

        val productPriceTextView = view.findViewById<TextView>(R.id.txtPriceProductDetail)
        productPriceTextView.text = "$productPrice"

        val productStockTextView = view.findViewById<TextView>(R.id.txtStockProductDetail)
        productStockTextView.text = "1"

        val productDescriptionTextView = view.findViewById<TextView>(R.id.txtDescriptionProduct)
        productDescriptionTextView.text = productDescription

        val productImageView = view.findViewById<ImageView>(R.id.imgDetailProduct)

        Glide.with(requireContext())
            .load(productImageUri)
            .placeholder(R.drawable.add)
            .error(R.drawable.add)
            .into(productImageView)

        val btnIncrement = view.findViewById<ImageView>(R.id.btnIncremetProduct)
        val btnDecrement = view.findViewById<ImageView>(R.id.btnDecrementProduct)
        val txtStock = view.findViewById<TextView>(R.id.txtStockProductDetail)

        var currentStock = 1
        val maxStock = productStock

        btnIncrement.setOnClickListener {
            if (currentStock < maxStock) {
                currentStock++
                txtStock.text = currentStock.toString()
            }
        }

        btnDecrement.setOnClickListener {
            if (currentStock > 1) {
                currentStock--
                txtStock.text = currentStock.toString()
            }
        }

        val btnAgregarAlCarrito = view.findViewById<Button>(R.id.btn_add_cart)
        btnAgregarAlCarrito.setOnClickListener {
            val mainActivity = activity as MainActivity
            mainActivity.onAddToCart(productName, productImageUri, currentStock, productPrice)
        }


        val btnBackProduct = view.findViewById<ImageView>(R.id.btnBackProductos)
        btnBackProduct.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }

        return view

    }

    companion object {

        @JvmStatic
        fun newInstance(nombreProducto: String, precioProducto: Double, stockProducto: Int, descripcionProducto: String, imagenProductoUri: String) =
            DetalleProduct().apply {
                arguments = Bundle().apply {
                    putString("nombreProducto", nombreProducto)
                    putDouble("precioProducto", precioProducto)
                    putInt("stockProducto", stockProducto)
                    putString("descripcionProducto", descripcionProducto)
                    putString("imagenProductoUri", imagenProductoUri)
                }
            }
    }
}
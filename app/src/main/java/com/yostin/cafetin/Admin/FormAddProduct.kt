package com.yostin.cafetin.Admin

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.yostin.cafetin.Entity.Category
import com.yostin.cafetin.Entity.Producto
import com.yostin.cafetin.Esquema.Crud.CategoryCRUD
import com.yostin.cafetin.Esquema.Crud.ProductoCRUD
import com.yostin.cafetin.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val PICK_IMAGE_REQUEST = 1

class FormAddProduct : Fragment() {
    private lateinit var btnUploadImage: Button
    private lateinit var iwLoadProductImage: ImageView
    private lateinit var selectedImageUri: Uri
    private var crud: ProductoCRUD? = null
    private var categoryId: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnUploadImage = view.findViewById(R.id.btnUploadImage)
        iwLoadProductImage = view.findViewById(R.id.iwLoadProductImage)
        val nombreProducto = view.findViewById<EditText>(R.id.inpNameProduct)
        val precioProducto = view.findViewById<EditText>(R.id.inpPriceProduct)
        val stockProducto = view.findViewById<EditText>(R.id.inpStockProducto)
        val descripcionProducto = view.findViewById<EditText>(R.id.inpDescripcionProducto)
        val buttonAdd = view.findViewById<Button>(R.id.btnGuardarProducto)

        crud = ProductoCRUD(requireContext())

        categoryId = arguments?.getInt("categoryId") ?: 0
        buttonAdd.setOnClickListener {
            val producto = Producto(null,nombreProducto.text.toString(), precioProducto.text.toString().toDouble(),
                stockProducto.text.toString().toInt(), categoryId,  descripcionProducto.text.toString(), selectedImageUri.toString())
            crud?.newProduct(producto)

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Mensaje Informativo")
            builder.setMessage("Producto Agregado exitosamente")
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                val categoriesAdminFragment = CategoriesAdmin()
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.fragment_admin_categoria, categoriesAdminFragment)
                    addToBackStack(null)
                    commit()
                }
            }, 2000)

        }

        btnUploadImage.setOnClickListener {
            openGallery()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_form_add_product, container, false)

    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data ?: return
            iwLoadProductImage.setImageURI(selectedImageUri)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FormAddProduct().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        }
    }
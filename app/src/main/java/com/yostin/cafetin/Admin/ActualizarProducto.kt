package com.yostin.cafetin.Admin



import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yostin.cafetin.Esquema.Crud.ProductoCRUD
import com.bumptech.glide.Glide
import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import com.yostin.cafetin.Entity.Producto
import com.yostin.cafetin.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val PICK_IMAGE_REQUEST = 1

class ActualizarProducto : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var btnUploadImage: Button
    private lateinit var iwLoadProductImage: ImageView
    private lateinit var btnActualizarProducto: Button
    private lateinit var inpNameProduct: EditText
    private var selectedImageUri: Uri? = null

    private lateinit var inpPriceProduct: EditText
    private lateinit var inpStockProducto: EditText
    private lateinit var inpDescripcionProducto: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            btnUploadImage = view.findViewById(R.id.btnUploadImage)
            iwLoadProductImage = view.findViewById(R.id.iwLoadProductImage)
            btnActualizarProducto = view.findViewById(R.id.btnActualizarProducto)

            inpNameProduct = view.findViewById(R.id.inpNameProduct)
            inpPriceProduct = view.findViewById(R.id.inpPriceProduct)
            inpStockProducto = view.findViewById(R.id.inpStockProducto)
            inpDescripcionProducto = view.findViewById(R.id.inpDescripcionProducto)

            val floatingActionButton = requireActivity().findViewById<FloatingActionButton>(R.id.btnAddProduct)
            floatingActionButton.visibility = View.GONE

            btnActualizarProducto.setOnClickListener {
                val nuevoNombreProducto = inpNameProduct.text.toString()
                val nuevoPrecioProducto = inpPriceProduct.text.toString().toDoubleOrNull()
                val nuevoStockProducto = inpStockProducto.text.toString().toIntOrNull()
                val nuevaDescripcionProducto = inpDescripcionProducto.text.toString()
                val nuevaImagenUri: String = selectedImageUri?.toString() ?: ""
                val mismaCategoria: Int? = arguments?.getInt("idCategoria")


                if (nuevoNombreProducto.isNotEmpty() && nuevoPrecioProducto != null && nuevoStockProducto != null) {


                    val productoCRUD = ProductoCRUD(requireContext())

                    arguments?.getInt("idProduct")?.let { idProducto ->
                        productoCRUD.updateProduct(Producto(idProducto, nuevoNombreProducto, nuevoPrecioProducto, nuevoStockProducto, mismaCategoria, nuevaDescripcionProducto, nuevaImagenUri))
                    }

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Mensaje Informativo")
                    builder.setMessage("Producto Actualizado exitosamente")
                    builder.setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    val alertDialog = builder.create()
                    alertDialog.show()

                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        val productsAdminFragment = CategoriesAdmin()
                        activity?.supportFragmentManager?.beginTransaction()?.apply {
                            replace(R.id.fragment_admin_categoria, productsAdminFragment)
                            addToBackStack(null)
                            commit()
                        }
                    }, 2000)
                } else {
                    // Mostrar un mensaje de error al usuario
                    // Por ejemplo, si el nombre del producto está vacío o si el precio o el stock no son números válidos
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_actualizar_producto, container, false)

        val productName = arguments?.getString("productName")
        val imageUri = arguments?.getString("imageUri")
        val precioProducto = arguments?.getDouble("productPrice").toString()
        val stockProducto = arguments?.getInt("productStock").toString()
        val descripcionProducto = arguments?.getString("productDescription")

        inpNameProduct = view.findViewById(R.id.inpNameProduct)
        productName?.let { inpNameProduct.setText(it) }

        inpPriceProduct = view.findViewById(R.id.inpPriceProduct)
        precioProducto.let { inpPriceProduct.setText(it) }

        inpStockProducto = view.findViewById(R.id.inpStockProducto)
        stockProducto.let { inpStockProducto.setText(it) }

        inpDescripcionProducto = view.findViewById(R.id.inpDescripcionProducto)
        descripcionProducto.let { inpDescripcionProducto.setText(it) }

        val iwLoadUpdateImage = view.findViewById<ImageView>(R.id.iwLoadProductImage)
        imageUri?.let {
            selectedImageUri = Uri.parse(it)
            Glide.with(requireContext())
                .load(selectedImageUri)
                .placeholder(R.drawable.icon_camera)
                .error(R.drawable.icon_camera)
                .into(iwLoadUpdateImage)
        }

        btnUploadImage = view.findViewById(R.id.btnUploadImage)
        iwLoadProductImage = view.findViewById(R.id.iwLoadProductImage)

        btnUploadImage.setOnClickListener{
            openGallery()
        }
        return view
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            iwLoadProductImage.setImageURI(selectedImageUri)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ActualizarProducto().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
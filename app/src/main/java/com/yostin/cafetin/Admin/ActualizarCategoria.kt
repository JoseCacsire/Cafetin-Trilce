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
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yostin.cafetin.Entity.Category
import com.yostin.cafetin.Esquema.Crud.CategoryCRUD
import com.yostin.cafetin.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val PICK_IMAGE_REQUEST = 1

class ActualizarCategoria : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var btnUpdateImage: Button
    private lateinit var iwLoadUpdateCategoryImage: ImageView
    private lateinit var btnUpdateCategoria: Button
    private lateinit var inpNameProduct: EditText
    private  var selectedImageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            btnUpdateImage = view.findViewById(R.id.btnUpdateImage)
            iwLoadUpdateCategoryImage = view.findViewById(R.id.iwLoadUpdateImage)
            btnUpdateCategoria = view.findViewById(R.id.btnUpdateCategoria)

            inpNameProduct = view.findViewById(R.id.inpNameUpdateProduct)

            val floatingActionButton = requireActivity().findViewById<FloatingActionButton>(R.id.btnAddCategoria)
            floatingActionButton.visibility = View.GONE

            btnUpdateCategoria.setOnClickListener {
                // Obtener el nuevo nombre de la categoría
                val nuevoNombreCategoria = inpNameProduct.text.toString()

                // Obtener la nueva URI de la imagen
                val nuevaImagenUri: String = selectedImageUri.toString()

                // Verificar que el nombre de la categoría no esté vacío
                if (nuevoNombreCategoria.isNotEmpty()) {
                    // Crear una instancia de CategoryCRUD
                    val categoryCRUD = CategoryCRUD(requireContext())

                    // Actualizar la categoría usando el CRUD
                    arguments?.getInt("idCategory")?.let { idCategoria ->
                        categoryCRUD.updateCategory(Category(idCategoria, nuevoNombreCategoria, nuevaImagenUri))
                    }

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Mensaje Informativo")
                    builder.setMessage("Categoría Agregada exitosamente")
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
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_actualizar_categoria, container, false)

        // Obtener los argumentos pasados al fragmento
        val categoryName = arguments?.getString("categoryName")
        val imageUri = arguments?.getString("imageUri")

        // Buscar el EditText y establecer el nombre de la categoría
        inpNameProduct = view.findViewById(R.id.inpNameUpdateProduct)
        categoryName?.let { inpNameProduct.setText(it) }

        // Buscar el ImageView y cargar la imagen
        val iwLoadUpdateImage = view.findViewById<ImageView>(R.id.iwLoadUpdateImage)
        imageUri?.let {
            selectedImageUri = Uri.parse(it)
            Glide.with(requireContext())
                .load(selectedImageUri)
                .placeholder(R.drawable.icon_camera)
                .error(R.drawable.icon_camera)
                .into(iwLoadUpdateImage)
        }


        btnUpdateImage = view.findViewById(R.id.btnUpdateImage)
        iwLoadUpdateCategoryImage = view.findViewById(R.id.iwLoadUpdateImage)

        btnUpdateImage.setOnClickListener{
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
            selectedImageUri = data.data ?: return
            iwLoadUpdateCategoryImage.setImageURI(selectedImageUri)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ActualizarCategoria().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


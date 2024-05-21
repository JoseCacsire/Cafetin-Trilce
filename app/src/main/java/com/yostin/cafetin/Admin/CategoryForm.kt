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

import android.widget.Toast
import com.yostin.cafetin.Entity.Category
import com.yostin.cafetin.Esquema.Crud.CategoryCRUD
import com.yostin.cafetin.R



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
//Constante para identificar la solicitud de selección de imagen
private const val PICK_IMAGE_REQUEST = 1

class CategoryForm : Fragment() {
    // Variables relacionadas con la interfaz de usuario
    private lateinit var btnUploadImage: Button
    private lateinit var iwLoadCategoryImage: ImageView
    // Variable para almacenar la URI de la imagen seleccionada
    private lateinit var selectedImageUri: Uri
    private var crud: CategoryCRUD? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnUploadImage = view.findViewById(R.id.btnUploadImage)
        iwLoadCategoryImage = view.findViewById(R.id.iwLoadCategoryImage)
        val nombreCategory = view.findViewById<EditText>(R.id.inpNameProduct)
        val buttonCategory = view.findViewById<Button>(R.id.btnAgregarCategoria)

        crud = CategoryCRUD(requireContext())

        buttonCategory.setOnClickListener {
            val category = Category(null, nombreCategory.text.toString(), selectedImageUri.toString())
            crud?.newCategory(category)

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

        // Manejador del evento clic en el botón para cargar la imagen desde la galería
        btnUploadImage.setOnClickListener {
            openGallery()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_form, container, false)
    }

    // Método para abrir la galería de imágenes
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    // Método para manejar el resultado de la selección de imagen desde la galería
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // Se obtiene la URI de la imagen seleccionada y se muestra en el ImageView
            selectedImageUri = data.data ?: return
            iwLoadCategoryImage.setImageURI(selectedImageUri)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryForm().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
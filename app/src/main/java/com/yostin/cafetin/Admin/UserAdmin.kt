package com.yostin.cafetin.Admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.yostin.cafetin.Login
import com.yostin.cafetin.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class UserAdmin : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_admin, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesion)

        btnCerrarSesion.setOnClickListener{
            firebaseAuth.signOut();
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
            Toast.makeText(requireContext(),"Cerraste sesion exitosamente", Toast.LENGTH_SHORT).show();
        }
        return view    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserAdmin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
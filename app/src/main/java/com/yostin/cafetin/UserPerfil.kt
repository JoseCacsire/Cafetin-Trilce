package com.yostin.cafetin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.yostin.cafetin.User.MisPedidos


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class UserPerfil : Fragment() {
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

        val view = inflater.inflate(R.layout.fragment_user_perfil, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        val btnMisPedidos = view.findViewById<Button>(R.id.btnMisPedidos)
        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesion)

        btnMisPedidos.setOnClickListener {
            val userId = arguments?.getInt(ARG_USER_ID) ?: -1 // Obtener userId del argumento
            val intent = Intent(requireContext(), MisPedidos::class.java)
            intent.putExtra("userId", userId) // Pasar userId como extra
            startActivity(intent)
        }


        btnCerrarSesion.setOnClickListener{
            firebaseAuth.signOut();
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
            Toast.makeText(requireContext(),"Cerraste sesion exitosamente", Toast.LENGTH_SHORT).show();
        }
        return view
    }

    companion object {
        private const val ARG_USER_ID = "userId"

        fun newInstance(userId: Int) =
            UserPerfil().apply {
                arguments = Bundle().apply {
                    putInt(ARG_USER_ID, userId)
                }
            }
    }
}
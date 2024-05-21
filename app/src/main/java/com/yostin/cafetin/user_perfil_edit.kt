package com.yostin.cafetin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class user_perfil_edit : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_user_perfil_edit, container, false)
        val iwBack = view.findViewById<ImageView>(R.id.btnBack)
        iwBack.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply{
                replace(R.id.fragment_container_edit_user,UserPerfil())
                addToBackStack(null)
                commit()
            }
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            user_perfil_edit().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
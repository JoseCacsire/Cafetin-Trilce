package com.yostin.cafetin.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yostin.cafetin.R

class MainActivityAdmin : AppCompatActivity() {

    private lateinit var btnView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)
        asignarReferenceAdmin()
        replaceFragmentAdmin(CategoriesAdmin())

    }

    private fun replaceFragmentAdmin(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_admin, fragment)
            .commit()
    }

    private fun asignarReferenceAdmin(){
        btnView = findViewById(R.id.btnNavAdmin)
        btnView.setOnNavigationItemSelectedListener{menuItem ->
            when(menuItem.itemId){
                R.id.navCategoriesAdmin ->{
                    replaceFragmentAdmin(CategoriesAdmin())
                    true
                }
                R.id.navPedidosAdmin ->{
                    replaceFragmentAdmin(PedidosAdmin())
                    true
                }
                R.id.navUserAdmin ->{
                    replaceFragmentAdmin(UserAdmin())
                    true
                }
                else -> false
            }
        }
    }
}
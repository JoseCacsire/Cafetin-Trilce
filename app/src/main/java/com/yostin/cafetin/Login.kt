package com.yostin.cafetin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yostin.cafetin.Admin.MainActivityAdmin
import com.yostin.cafetin.Esquema.Crud.UserCRUD
import com.yostin.cafetin.databinding.ActivityLoginBinding


class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val EMAILADMIN = "admin@gmail.com";
    private lateinit var userCRUD: UserCRUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        userCRUD = UserCRUD(this)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.txtRegistrate.setOnClickListener{
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }


        binding.btnLogin.setOnClickListener {
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = firebaseAuth.currentUser
                        val userId = userCRUD.getUserIdByEmail(email)
                        Log.d("BuscarId", "id: $userId")
                        val intent = if (email.equals(EMAILADMIN)) {
                            Toast.makeText(this, "Inicio de sesión exitoso como Administrador", Toast.LENGTH_SHORT).show()
                            Intent(this, MainActivityAdmin::class.java)
                        } else {
                            Toast.makeText(this, "Inicio de sesión exitoso como Usuario", Toast.LENGTH_SHORT).show()
                            Intent(this, MainActivity::class.java)
                        }
                        intent.putExtra("userId", userId)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.w("MainActivity", "Error en el inicio de sesión", task.exception)
                        Toast.makeText(this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
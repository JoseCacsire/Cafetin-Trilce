package com.yostin.cafetin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yostin.cafetin.Entity.User
import com.yostin.cafetin.Esquema.Crud.UserCRUD

class Register : AppCompatActivity() {

    private lateinit var txtNombApell : EditText
    private lateinit var txtEmail : EditText
    private lateinit var txtPhone: EditText
    private lateinit var txtPassword : EditText
    private lateinit var btnRegister: Button

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAuth = FirebaseAuth.getInstance()

        txtNombApell = findViewById(R.id.txtNombApell)
        txtEmail = findViewById(R.id.txtEmail)
        txtPhone = findViewById(R.id.txtPhone)
        txtPassword = findViewById(R.id.txtPassword)

        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            var crud:UserCRUD? = null

            val nombreUser = txtNombApell.text.toString()
            val phoneUser = txtPhone.text.toString()
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            crud = UserCRUD(this)

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("MainActivity", "Registro exitoso")
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        
                        val user: FirebaseUser? = firebaseAuth.currentUser

                        crud?.newUser(User(null, nombreUser, email, phoneUser, password))

                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.w("MainActivity", "Error en el registro", task.exception)
                        Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
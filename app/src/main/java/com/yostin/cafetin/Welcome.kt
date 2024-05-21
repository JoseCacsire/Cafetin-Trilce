package com.yostin.cafetin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yostin.cafetin.databinding.ActivityWelcomeBinding

class Welcome : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
 
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}

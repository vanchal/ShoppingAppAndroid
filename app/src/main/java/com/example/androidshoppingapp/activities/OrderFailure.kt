package com.example.androidshoppingapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.androidshoppingapp.R

class OrderFailure : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_failure)

        val backBtn = findViewById<TextView>(R.id.home_btn)

        backBtn.setOnClickListener{
            val intent = Intent (this,Cart1::class.java)
            startActivity(intent)
        }
    }
}
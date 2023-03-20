package com.example.androidshoppingapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidshoppingapp.R
import java.util.*

class OrderSuccess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)

        var transactionNumber = findViewById<TextView>(R.id.transaction_num)
        val min = 1000000000;
        val random_int = Math.floor(Math.random() *  min).toInt()
        transactionNumber.text = random_int.toString();

        val backBtn = findViewById<TextView>(R.id.home_btn)

        backBtn.setOnClickListener{
            val intent = Intent (this,LandingPage::class.java)
            startActivity(intent)
        }
    }
}
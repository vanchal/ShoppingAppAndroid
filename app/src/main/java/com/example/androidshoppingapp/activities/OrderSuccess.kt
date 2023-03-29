package com.example.androidshoppingapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidshoppingapp.R
import java.util.*


class OrderSuccess : AppCompatActivity() {

    override fun onBackPressed() {
        val intent = Intent (this,LandingPage::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)

        var transactionNumber = findViewById<TextView>(R.id.transaction_num)
        val min = 1000000000;
        val random_int = Math.floor(Math.random() *  min).toInt()
        transactionNumber.text = random_int.toString();

        val backBtn = findViewById<TextView>(R.id.home_btn)
        CartFunc.getInstance(applicationContext).emptyCart()
        Cart1.items.forEach {
            it.quantity = 0;
        }

        backBtn.setOnClickListener{
           onBackPressed()
        }
    }


}
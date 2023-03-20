package com.example.androidshoppingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.example.androidshoppingapp.activities.LandingPage
import com.example.androidshoppingapp.activities.Login
import com.example.androidshoppingapp.helper.SharedPrefManager

class MainActivity : AppCompatActivity() {
    lateinit var sharedPrefManager : SharedPrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            // on below line we are
            // creating a new intent
//            Log.e("loginState", "${sharedPrefManager.isLoggedIn}")
            if(SharedPrefManager.getInstance(applicationContext).isLoggedIn){
                val intent = Intent(this, LandingPage::class.java)
                this.startActivity(intent)
            }
            else{
                val intent = Intent(this@MainActivity, Login::class.java)
                startActivity(intent);
            }

//            val i = Intent(
//                this@MainActivity,
//                Register::class.java
//            )
//            startActivity(i)
            finish()
        }, 1000)
    }
}
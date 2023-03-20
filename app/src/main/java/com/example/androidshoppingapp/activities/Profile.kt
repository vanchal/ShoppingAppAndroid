package com.example.androidshoppingapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidshoppingapp.R
import com.example.androidshoppingapp.api.ApiInterface
import com.example.androidshoppingapp.api.RetrofitClient
import com.example.androidshoppingapp.helper.AppHelper
import com.example.androidshoppingapp.helper.SharedPrefManager
import com.example.androidshoppingapp.models.UserProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class Profile : AppCompatActivity() {
    var progressBar: ProgressBar? = null
    lateinit var retrofit: Retrofit
    lateinit var apiInterface: ApiInterface
    lateinit var email : TextView
    lateinit var name : TextView
    lateinit var phone : TextView
    lateinit var img : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initData()
        userProfile()
        var logoutBtn = findViewById<TextView>(R.id.logout_btn)

        logoutBtn.setOnClickListener{
            SharedPrefManager.getInstance(applicationContext).logout()
            val intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        phone = findViewById<TextView>(R.id.profilenumber)
        phone.text = 9876543210.toString()

        var cartIcon = findViewById<ImageView>(R.id.cart)
        cartIcon.setOnClickListener {
            val intent = Intent(this,Cart1::class.java)
            startActivity(intent)
        }

        var backBtn = findViewById<ImageView>(R.id.backbtn)
        backBtn.setOnClickListener {
            finish()
        }

    }

    private fun initData() {
        /*Creating the instance of retrofit */
        retrofit = RetrofitClient.getInstance()

        /*Get the reference of Api interface*/
        apiInterface = retrofit.create(ApiInterface::class.java)
    }


    private fun userProfile() {

        if(AppHelper.isConnected(this))
        {
            progressBar?.visibility = View.VISIBLE
            var token = SharedPrefManager.getInstance(applicationContext).token.token.toString()

            apiInterface.getUserProfile(token = "Bearer $token").enqueue(object :
                Callback<UserProfileResponse> {
                override fun onResponse(call: Call<UserProfileResponse>,
                                        response: Response<UserProfileResponse>
                ) {
                    progressBar?.visibility = View.GONE

                    if (response.isSuccessful) {
                       email = findViewById<TextView>(R.id.profilemail)
                       name = findViewById<TextView>(R.id.profilename)


                        email.text = response.body()?.email
                        name.text = response.body()?.username

                    } else {
                        Toast.makeText(this@Profile, "Didn't get User!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                    progressBar?.visibility = View.GONE

                    t.printStackTrace()
                    Toast.makeText(this@Profile, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
        else
        {
            Toast.makeText(this@Profile, "Please check you internet connection", Toast.LENGTH_LONG).show()
        }
    }

}
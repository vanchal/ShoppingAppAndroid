package com.example.androidshoppingapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.androidshoppingapp.R
import com.example.androidshoppingapp.api.ApiInterface
import com.example.androidshoppingapp.api.RetrofitClient
import com.example.androidshoppingapp.helper.AppHelper
import com.example.androidshoppingapp.helper.SharedPrefManager
import com.example.androidshoppingapp.models.LoginRequest
import com.example.androidshoppingapp.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class Login : AppCompatActivity() {
    var progressBar: ProgressBar? = null
    lateinit  var retrofit: Retrofit
    lateinit var apiInterface: ApiInterface
    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var loginButton : Button
    lateinit var register : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initData()


        loginButton = findViewById<Button>(R.id.loginButton)
        register = findViewById<TextView>(R.id.register)
        email = findViewById<EditText>(R.id.emailIntput)
        password = findViewById<EditText>(R.id.passwordInput)

        loginButton.setOnClickListener {

            val emailRegister = email.text.toString().trim()
            val passRegister = password.text.toString().trim()

            if (emailRegister.isEmpty()  || passRegister.isEmpty()
            ) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailRegister).matches()) {
                email.error = "Invalid Email"
            } else {
                val userLoginData: LoginRequest = LoginRequest(
                    email = emailRegister,
                    password = passRegister,
                )
                loginUser(userLoginData)
            }

        }

        register.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }


    }

    private fun initData() {
        /*Creating the instance of retrofit */
        retrofit = RetrofitClient.getInstance()

        /*Get the reference of Api interface*/
        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    private fun loginUser(userData : LoginRequest){

        if(AppHelper.isConnected(this))
        {
            progressBar?.visibility = View.VISIBLE

            apiInterface.loginRegisterUser(userData).enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>,
                                        response: Response<LoginResponse>
                ) {
                    progressBar?.visibility = View.GONE

                    if (response.isSuccessful) {
                        var token = response.body();
//                        sharedPrefManager.saveUserToken(token)
                        SharedPrefManager.getInstance(applicationContext).saveUserToken(token)
//                        Log.e("loginState", "${sharedPrefManager.isLoggedIn}")
//                        Log.e("token", "${sharedPrefManager.token}")
                       val intent = Intent(this@Login, LandingPage::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        this@Login.startActivity(intent)
                    } else {
                        Toast.makeText(this@Login, "Login failed!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    progressBar?.visibility = View.GONE

                    t.printStackTrace()
                    Toast.makeText(this@Login, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
        else
        {
            Toast.makeText(this@Login, "Please check you internet connection", Toast.LENGTH_LONG).show()
        }
    }

//    override fun onStart() {
//        super.onStart()
//        if(sharedPrefManager.isLoggedIn){
//            val intent = Intent(this@Login, LandingPage::class.java)
//            this@Login.startActivity(intent)
//        }
//    }



}





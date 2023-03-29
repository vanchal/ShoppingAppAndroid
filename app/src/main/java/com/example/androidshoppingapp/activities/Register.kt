package com.example.androidshoppingapp.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import com.example.androidshoppingapp.R
import com.example.androidshoppingapp.api.ApiInterface
import com.example.androidshoppingapp.api.RetrofitClient
import com.example.androidshoppingapp.helper.AppHelper
import com.example.androidshoppingapp.models.RegisterRequest
import com.example.androidshoppingapp.models.RegisterResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class Register : AppCompatActivity() {
    lateinit var email : EditText
    lateinit var username : EditText
    lateinit var password : EditText
    lateinit var confirmPassword : EditText
    lateinit var registerButton : Button
    lateinit var loginText : TextView
    lateinit  var retrofit: Retrofit
    lateinit var apiInterface: ApiInterface
    var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initData()

        email = findViewById<EditText>(R.id.emailIntput)
        password = findViewById<EditText>(R.id.passwordInput)
        username = findViewById<EditText>(R.id.nameInput)
        confirmPassword = findViewById<EditText>(R.id.confirmPasswordInput)
        registerButton = findViewById<Button>(R.id.regButton)
        loginText = findViewById<TextView>(R.id.loginText)

        registerButton.setOnClickListener {

            val emailRegister = email.text.toString().trim()
            val usernameRegister = username.text.toString().trim()
            val passRegister = password.text.toString().trim()
            val cnfpassRegister = confirmPassword.text.toString().trim()

            if (emailRegister.isEmpty() || usernameRegister.isEmpty() || passRegister.isEmpty()
                || cnfpassRegister.isEmpty()
            ) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailRegister).matches()) {
                email.error = "Invalid Email"
            } else if (passRegister != cnfpassRegister) {
                Log.i("pass", "onCreateView: ${passRegister} ${cnfpassRegister} ")
                email.text.clear()
                confirmPassword.text.clear()
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
            } else {
                val userRegistrationData: RegisterRequest = RegisterRequest(
                    email = emailRegister,
                    username = usernameRegister,
                    password = passRegister,
                )
                registerUser(userRegistrationData)
            }
        }

        loginText.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

    }

    private fun initData() {
        /*Creating the instance of retrofit */
        retrofit = RetrofitClient.getInstance()

        /*Get the reference of Api interface*/
        apiInterface = retrofit.create(ApiInterface::class.java)
    }


    private fun registerUser(userData : RegisterRequest){

        if(AppHelper.isConnected(this))
        {
            progressBar?.visibility = View.VISIBLE

            apiInterface.postRegisterUser(userData).enqueue(object :
                Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>,
                                        response: Response<RegisterResponse>
                ) {
                    progressBar?.visibility = View.GONE

                    if (response.isSuccessful) {
                        val intent = Intent(this@Register, Login::class.java)
                        this@Register.startActivity(intent)
                        Toast.makeText(this@Register, "Registration success!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this@Register, "password must be 8 characters long and must have a number,lower case,upper case and special char", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    progressBar?.visibility = View.GONE

                    t.printStackTrace()
                    Toast.makeText(this@Register, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
        else
        {
            Toast.makeText(this@Register, "Please check you internet connection", Toast.LENGTH_LONG).show()
        }
    }




}



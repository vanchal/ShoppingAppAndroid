package com.example.androidshoppingapp.helper

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.androidshoppingapp.models.LoginResponse

class SharedPrefManager private constructor (var context: Context) {

     fun saveUserToken(userToken : LoginResponse?) {
         Log.e("userToken", "${userToken?.token}")
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor?.putString("token", userToken?.token)
        editor?.putBoolean("LoginState", true)
        editor?.commit()
    }

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences!!.getBoolean("LoginState", false)
        }
    val token: LoginResponse
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return LoginResponse(sharedPreferences?.getString("token", ""))
        }

    fun logout() {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        var editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.commit()
    }


    companion object {
        private const val SHARED_PREF_NAME = "USER_TOKEN"
        private var mInstance : SharedPrefManager? = null
        @Synchronized
        fun getInstance(context: Context) : SharedPrefManager {
            if(mInstance == null){
                mInstance = SharedPrefManager(context)
            }
            return mInstance as SharedPrefManager
        }
    }


}




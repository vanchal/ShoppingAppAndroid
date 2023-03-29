package com.example.androidshoppingapp.activities

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import com.example.androidshoppingapp.api.ApiInterface
import com.example.androidshoppingapp.api.RetrofitClient
import com.example.androidshoppingapp.helper.AppHelper
import com.example.androidshoppingapp.helper.SharedPrefManager
import com.example.androidshoppingapp.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CartFunc private constructor(var context: Context){

    fun addToCart(item : AddItemCartRequest, btn : Button){
        initData()
        if(AppHelper.isConnected(context))
        {
            var token = SharedPrefManager.getInstance(context).token.token.toString()
            apiInterface.addItem(token = "Bearer $token",item).enqueue(object :
                Callback<AddItemCartResponse> {
                override fun onResponse(call: Call<AddItemCartResponse>,
                                        response: Response<AddItemCartResponse>
                ) {
                    if (response.isSuccessful) {
//                        btn.text = "Go To Cart"
//                        btn.setOnClickListener(){
//                            val intent = Intent(context,Cart1::class.java)
//                            context.startActivity(intent)
//                        }
                        Toast.makeText(context, "Item Added!", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(context, "failed!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<AddItemCartResponse>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
        else
        {
            Toast.makeText(context, "Please check you internet connection", Toast.LENGTH_LONG).show()
        }
    }


    fun deleteFromCart(productId : String){
        initData()
        if(AppHelper.isConnected(context))
        {
            var token = SharedPrefManager.getInstance(context).token.token.toString()
            apiInterface.deleteItem(token = "Bearer $token", productId).enqueue(object : Callback<DeleteItemResponse> {
                override fun onResponse(call: Call<DeleteItemResponse>,
                                        response: Response<DeleteItemResponse>
                ) {

                    if (response.isSuccessful) {
                        Toast.makeText(context, "Item Deleted!", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(context, "failed!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<DeleteItemResponse>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
        else
        {
            Toast.makeText(context, "Please check you internet connection", Toast.LENGTH_LONG).show()
        }
    }


    fun updateCart(productId: String?, quantity : UpdateCartRequest){
        initData()
        if(AppHelper.isConnected(context))
        {
            var token = SharedPrefManager.getInstance(context).token.token.toString()
            apiInterface.updateCartList(token = "Bearer $token", productId, quantity).enqueue(object :
                Callback<UpdateCartResponse> {
                override fun onResponse(call: Call<UpdateCartResponse>,
                                        response: Response<UpdateCartResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Item Updated!", Toast.LENGTH_LONG)

                    } else {
                        Toast.makeText(context, "failed!", Toast.LENGTH_LONG)
                    }
                }

                override fun onFailure(call: Call<UpdateCartResponse>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
        else
        {
            Toast.makeText(context, "Please check you internet connection", Toast.LENGTH_LONG).show()
        }


    }

    fun emptyCart(){
        initData()
        if(AppHelper.isConnected(context))
        {
            var token = SharedPrefManager.getInstance(context).token.token.toString()
            apiInterface.clearCart(token = "Bearer $token").enqueue(object : Callback<EmptyCartResponse> {
                override fun onResponse(call: Call<EmptyCartResponse>,
                                        response: Response<EmptyCartResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Cart Cleared!", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(context, "failed!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<EmptyCartResponse>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
        else
        {
            Toast.makeText(context, "Please check you internet connection", Toast.LENGTH_LONG).show()
        }
    }





    private fun initData() {
        /*Creating the instance of retrofit */
        retrofit = RetrofitClient.getInstance()

        /*Get the reference of Api interface*/
        apiInterface = retrofit.create(ApiInterface::class.java)
    }



    companion object {
        lateinit  var retrofit: Retrofit
        lateinit var apiInterface: ApiInterface
        private var cartInstance : CartFunc? = null
        @Synchronized
        fun getInstance(context: Context) : CartFunc {
            if(cartInstance == null){
                cartInstance = CartFunc(context)
            }
            return cartInstance as CartFunc
        }
    }


}
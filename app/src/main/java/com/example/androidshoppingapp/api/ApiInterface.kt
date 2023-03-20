package com.example.androidshoppingapp.api

import com.example.androidshoppingapp.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {

    @POST("/user/register")
    fun postRegisterUser(@Body register : RegisterRequest): Call<RegisterResponse>

    @POST("/user/login")
    fun loginRegisterUser(@Body userLogin: LoginRequest): Call<LoginResponse>

    @GET("/user/me")
    fun getUserProfile(@Header("Authorization") token: String?) : Call<UserProfileResponse>


    @GET("/products")
    fun getProductList(@Header("Authorization") token: String?) : Call<ProductListResponse>




    @GET("cart/getCart")
    fun getCartList(
        @Header("Authorization") token: String?,
    ): Call<CartListResponse>

    @POST("cart/addItemCart")
    fun addItem( @Header("Authorization") token: String?,@Body cartItem : AddItemCartRequest) : Call<AddItemCartResponse>

    @PUT("cart/updateCart/{productId}")
    fun updateCartList( @Header("Authorization") token: String?, @Path(value = "productId") productId : String?, @Body quantity : UpdateCartRequest ) : Call<UpdateCartResponse>

    @DELETE("cart/deleteCart/{productId}")
    fun deleteItem( @Header("Authorization") token: String?, @Path(value = "productId") productId : String?) : Call<DeleteItemResponse>


    @POST("order/placeOrder")
    fun plcaeOrder(@Header("Authorization") token : String?, @Body order : OrderPlaceRequest) : Call<OrderPlaceResponse>
}
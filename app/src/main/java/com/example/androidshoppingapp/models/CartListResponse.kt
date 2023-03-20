package com.example.androidshoppingapp.models

import com.google.gson.annotations.SerializedName

data class CartListResponse(
	@field:SerializedName("data")
	val data: Data? = null
)

data class Data(

	@field:SerializedName("total_amount")
	val totalAmount: Int? = null,

	@field:SerializedName("cart_items")
	val cartItems: List<CartItemsItem?>? = null
)

data class CartItemsItem(

	@field:SerializedName("createdAt")
	val createdAt: Any? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("quantity")
	var quantity: Int = 1,

	@field:SerializedName("productId")
	val productId: String? = null,

	@field:SerializedName("price")
	val price: Int = 0,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: Any? = null
)

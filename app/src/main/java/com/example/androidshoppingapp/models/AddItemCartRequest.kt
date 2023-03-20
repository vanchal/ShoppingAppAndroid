package com.example.androidshoppingapp.models

import com.google.gson.annotations.SerializedName

data class AddItemCartRequest(

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("productId")
	val productId: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null
)

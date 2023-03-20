package com.example.androidshoppingapp.models

import com.google.gson.annotations.SerializedName

data class AddItemCartResponse(

	@field:SerializedName("item")
	val item: List<ItemItem?>? = null
)

data class ItemItem(

	@field:SerializedName("createdAt")
	val createdAt: Any? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("productId")
	val productId: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: Any? = null
)

package com.example.androidshoppingapp.models

import com.google.gson.annotations.SerializedName

data class ProductListResponse(

	@field:SerializedName("data")
	val data: ArrayList<DataItem?>? = null
)

data class DataItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("ispresent")
	var ispresent: Boolean? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("carousel")
	val carousel: List<String?>? = null
)

package com.example.androidshoppingapp.models

import com.google.gson.annotations.SerializedName

data class UpdateCartRequest(

	@field:SerializedName("quantity")
	val quantity: Int? = null
)

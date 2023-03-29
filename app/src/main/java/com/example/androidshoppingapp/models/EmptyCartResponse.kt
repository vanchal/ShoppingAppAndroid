package com.example.androidshoppingapp.models

import com.google.gson.annotations.SerializedName

data class EmptyCartResponse(

	@field:SerializedName("message")
	val message: String? = null
)

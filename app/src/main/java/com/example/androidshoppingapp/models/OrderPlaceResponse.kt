package com.example.androidshoppingapp.models

import com.google.gson.annotations.SerializedName

data class OrderPlaceResponse(

	@field:SerializedName("message")
	val message: String? = null
)

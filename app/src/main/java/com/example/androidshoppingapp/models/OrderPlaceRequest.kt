package com.example.androidshoppingapp.models

import com.google.gson.annotations.SerializedName

data class OrderPlaceRequest(

	@field:SerializedName("total")
	val total: Int? = null
)

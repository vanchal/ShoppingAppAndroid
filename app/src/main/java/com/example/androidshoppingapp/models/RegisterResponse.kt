package com.example.androidshoppingapp.models

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

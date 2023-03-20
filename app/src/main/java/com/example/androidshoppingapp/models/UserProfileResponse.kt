package com.example.androidshoppingapp.models

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

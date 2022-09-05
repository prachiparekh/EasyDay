package com.civil.easyday.app.sources.remote.model

import com.google.gson.annotations.SerializedName

data class UserModel(

	@field:SerializedName("profession")
	val profession: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("profile_image")
	val profileImage: Any? = null,

	@field:SerializedName("is_invisible")
	val isInvisible: Boolean? = null,

	@field:SerializedName("device_token")
	val deviceToken: Any? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: Long? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

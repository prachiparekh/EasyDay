package com.app.easyday.app.sources.remote.model

import com.google.gson.annotations.SerializedName

data class ProjectInvitedItem(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("project_id")
    val projectId: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)

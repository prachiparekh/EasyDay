package com.civil.easyday.app.sources.remote.model

import com.google.gson.annotations.SerializedName

data class AddProjectRequestModel(

    @field:SerializedName("assign_color")
    val assignColor: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("project_name")
    val projectName: String? = null,

    @field:SerializedName("participants")
    val participants: List<ProjectParticipantsModel?>? = null
)


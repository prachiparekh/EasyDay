package com.app.easyday.app.sources.remote.model

import com.app.easyday.app.sources.local.model.ContactModel
import com.google.gson.annotations.SerializedName

data class AddTaskRequestModel(

    @field:SerializedName("project_id")
    val project_id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("priority")
    val priority: Int? = null,

    @field:SerializedName("red_flag")
    val red_flag: Int? = null,

    @field:SerializedName("due_date")
    val due_date: String? = null,

    @field:SerializedName("tags")
    var tags: ArrayList<Int>? = null,

    @field:SerializedName("zones")
    var zones: ArrayList<Int>? = null,

    @field:SerializedName("spaces")
    var spaces: ArrayList<Int>? = null,

    @field:SerializedName("task_participants")
    var task_participants: ArrayList<Int>? = null
)

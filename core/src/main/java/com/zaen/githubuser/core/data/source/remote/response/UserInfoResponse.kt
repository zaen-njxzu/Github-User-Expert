package com.zaen.githubuser.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    val id: Int? = null,
    @field:SerializedName("avatar_url")
    val profileImageUrl: String,
    @field:SerializedName("login")
    val username: String,
    @field:SerializedName("html_url")
    val userGithubUrl: String
)
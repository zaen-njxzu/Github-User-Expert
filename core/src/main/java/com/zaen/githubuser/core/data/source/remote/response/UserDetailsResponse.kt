package com.zaen.githubuser.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetailsResponse(
    @field:SerializedName("avatar_url")
    val profileImageUrl: String,
    @field:SerializedName("created_at")
    val createdAt: String,
    val followers: Int,
    val following: Int,
    @field:SerializedName("html_url")
    val userGithubUrl: String,
    @field:SerializedName("login")
    val username: String,
    val name: String,
    @field:SerializedName("public_repos")
    val publicRepos: Int
)
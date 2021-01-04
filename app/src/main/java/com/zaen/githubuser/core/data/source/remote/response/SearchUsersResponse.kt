package com.zaen.githubuser.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchUsersResponse(
    val incomplete_results: Boolean,
    @SerializedName("items")
    val users_info: MutableList<UserInfoResponse>,
    val total_count: Int
)
package com.zaen.githubuser.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDetails(
    val profileImageUrl: String,
    val createdAt: String,
    val followers: Int,
    val following: Int,
    val userGithubUrl: String,
    val username: String,
    val name: String,
    val publicRepos: Int
): Parcelable

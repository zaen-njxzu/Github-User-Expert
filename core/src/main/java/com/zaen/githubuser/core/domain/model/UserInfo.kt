package com.zaen.githubuser.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo (
    val id: Int? = null,
    val profileImageUrl: String,
    val username: String,
    val userGithubUrl: String
) : Parcelable
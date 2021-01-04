package com.zaen.githubuser.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_info"
)
data class UserInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "profile_image_url")
    val profileImageUrl: String,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "user_github_url")
    val userGithubUrl: String
)
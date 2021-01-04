package com.zaen.githubuser.core.domain.repository

import com.zaen.githubuser.core.data.Resource
import com.zaen.githubuser.core.domain.model.UserDetails
import com.zaen.githubuser.core.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun searchUsers(
        usernameQuery: String,
        pageNumber: Int
    ) : Flow<Resource<List<UserInfo>>>

    fun getUserDetails(
        username: String
    ) : Flow<Resource<UserDetails>>

    fun getFollowersUserData(
        username: String
    ) : Flow<Resource<List<UserInfo>>>

    fun getFollowingsUserData(
        username: String
    ) : Flow<Resource<List<UserInfo>>>

    suspend fun upsertUser(userInfo: UserInfo)

    fun getSavedUsers() : Flow<List<UserInfo>>

    suspend fun deleteUser(userInfo: UserInfo)
}
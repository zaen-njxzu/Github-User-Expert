package com.zaen.githubuser.core.domain.usecase

import com.zaen.githubuser.core.data.Resource
import com.zaen.githubuser.core.domain.model.UserDetails
import com.zaen.githubuser.core.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
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

    suspend fun saveUser(userInfo: UserInfo)

    fun getSavedUsers() : Flow<List<UserInfo>>

    suspend fun deleteUser(userInfo: UserInfo)
}
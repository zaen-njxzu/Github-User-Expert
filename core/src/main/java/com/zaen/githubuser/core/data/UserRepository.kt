package com.zaen.githubuser.core.data

import com.zaen.githubuser.core.data.source.local.LocalDataSource
import com.zaen.githubuser.core.data.source.remote.RemoteDataSource
import com.zaen.githubuser.core.data.source.remote.network.ApiResponse
import com.zaen.githubuser.core.data.source.remote.response.UserDetailsResponse
import com.zaen.githubuser.core.data.source.remote.response.UserInfoResponse
import com.zaen.githubuser.core.domain.model.UserDetails
import com.zaen.githubuser.core.domain.model.UserInfo
import com.zaen.githubuser.core.domain.repository.IUserRepository
import com.zaen.githubuser.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IUserRepository{
    override fun searchUsers(
        usernameQuery: String,
        pageNumber: Int
    ): Flow<Resource<List<UserInfo>>> =
        object : TransformResource<List<UserInfo>, List<UserInfoResponse>>() {
            override fun transformResponse(data: List<UserInfoResponse>): List<UserInfo> =
                DataMapper.mapResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<List<UserInfoResponse>>> =
                remoteDataSource.searchUsers(usernameQuery, pageNumber)
        }.asFlow()

    override fun getUserDetails(username: String): Flow<Resource<UserDetails>> =
        object : TransformResource<UserDetails, UserDetailsResponse>() {
            override fun transformResponse(data: UserDetailsResponse): UserDetails =
                DataMapper.mapResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<UserDetailsResponse>> =
                remoteDataSource.getUserDetails(username)
        }.asFlow()

    override fun getFollowersUserData(username: String): Flow<Resource<List<UserInfo>>> =
        object : TransformResource<List<UserInfo>, List<UserInfoResponse>>() {
            override fun transformResponse(data: List<UserInfoResponse>): List<UserInfo> =
                DataMapper.mapResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<List<UserInfoResponse>>> =
                remoteDataSource.getFollowersUserData(username)
        }.asFlow()

    override fun getFollowingsUserData(username: String): Flow<Resource<List<UserInfo>>> =
        object : TransformResource<List<UserInfo>, List<UserInfoResponse>>() {
            override fun transformResponse(data: List<UserInfoResponse>): List<UserInfo> =
                DataMapper.mapResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<List<UserInfoResponse>>> =
                remoteDataSource.getFollowingsUserData(username)
        }.asFlow()

    override suspend fun upsertUser(userInfo: UserInfo) {
        val userInfoEntity = DataMapper.mapDomainToEntity(userInfo)
        localDataSource.upsert(userInfoEntity)
    }

    override fun getSavedUsers(): Flow<List<UserInfo>> =
        localDataSource.getUsersInfo().map { DataMapper.mapEntitiesToDomain(it) }

    override suspend fun deleteUser(userInfo: UserInfo) {
        val userInfoEntity = DataMapper.mapDomainToEntity(userInfo)
        localDataSource.delete(userInfoEntity)
    }


}
package com.zaen.githubuser.core.data.source.remote

import android.util.Log
import com.zaen.githubuser.core.data.source.remote.network.ApiResponse
import com.zaen.githubuser.core.data.source.remote.network.ApiService
import com.zaen.githubuser.core.data.source.remote.response.UserDetailsResponse
import com.zaen.githubuser.core.data.source.remote.response.UserInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun searchUsers(
        usernameQuery: String,
        pageNumber: Int
    ) : Flow<ApiResponse<List<UserInfoResponse>>> {
        return flow {
            try {
                val response = apiService.searchUsers(usernameQuery, pageNumber)
                if(response.users_info.isNotEmpty()) {
                    emit(ApiResponse.Success(response.users_info))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserDetails(
        username: String
    ) : Flow<ApiResponse<UserDetailsResponse>> {
        return flow {
            try {
                val response = apiService.getUserDetails(username)
                emit(ApiResponse.Success(response))
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowersUserData(
        username: String
    ) : Flow<ApiResponse<List<UserInfoResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowersUserData(username)
                if(response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowingsUserData(
        username: String
    ) : Flow<ApiResponse<List<UserInfoResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowingsUserData(username)
                if(response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}


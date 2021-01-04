package com.zaen.githubuser.core.data.source.remote.network

import com.zaen.githubuser.core.BuildConfig
import com.zaen.githubuser.core.data.source.remote.response.UserDetailsResponse
import com.zaen.githubuser.core.data.source.remote.response.FollowUserResponse
import com.zaen.githubuser.core.data.source.remote.response.SearchUsersResponse
import com.zaen.githubuser.core.util.Constants.Companion.QUERY_PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q")
        usernameQuery: String,
        @Query("page")
        pageNumber: Int,
        @Query("per_page")
        resultPerPage: Int = QUERY_PAGE_SIZE,
        @Header("Authorization")
        token: String = BuildConfig.GITHUB_API_KEY
    ) : SearchUsersResponse

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username")
        username: String,
        @Header("Authorization")
        token: String = BuildConfig.GITHUB_API_KEY
    ) : UserDetailsResponse

    @GET("users/{username}/followers")
    suspend fun getFollowersUserData(
        @Path("username")
        username: String,
        @Header("Authorization")
        token: String = BuildConfig.GITHUB_API_KEY
    ) : FollowUserResponse

    @GET("users/{username}/following")
    suspend fun getFollowingsUserData(
        @Path("username")
        username: String,
        @Header("Authorization")
        token: String = BuildConfig.GITHUB_API_KEY
    ) : FollowUserResponse

}
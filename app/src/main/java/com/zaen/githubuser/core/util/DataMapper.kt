package com.zaen.githubuser.core.util

import com.zaen.githubuser.core.data.source.local.entity.UserInfoEntity
import com.zaen.githubuser.core.data.source.remote.response.UserDetailsResponse
import com.zaen.githubuser.core.data.source.remote.response.UserInfoResponse
import com.zaen.githubuser.core.domain.model.UserDetails
import com.zaen.githubuser.core.domain.model.UserInfo


object DataMapper {
    fun mapResponseToDomain(input: List<UserInfoResponse>): List<UserInfo> =
        input.map {
            UserInfo(
                it.id,
                it.profileImageUrl,
                it.username,
                it.userGithubUrl
            )
        }

    fun mapEntitiesToDomain(input: List<UserInfoEntity>): List<UserInfo> =
        input.map {
            UserInfo(
                it.id,
                it.profileImageUrl,
                it.username,
                it.userGithubUrl
            )
        }

    fun mapResponseToDomain(userDetailsResponse: UserDetailsResponse): UserDetails = userDetailsResponse.let {
        UserDetails(
            it.profileImageUrl,
            it.createdAt,
            it.followers,
            it.following,
            it.userGithubUrl,
            it.username,
            it.name,
            it.publicRepos
        )
    }

    fun mapDomainToEntity(userInfo: UserInfo): UserInfoEntity = userInfo.let {
        UserInfoEntity(
            it.id,
            it.profileImageUrl,
            it.username,
            it.userGithubUrl
        )
    }


}
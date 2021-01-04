package com.zaen.githubuser.core.domain.usecase

import com.zaen.githubuser.core.domain.model.UserInfo
import com.zaen.githubuser.core.domain.repository.IUserRepository

class UserInteractor(private val usersRepository: IUserRepository) : UserUseCase {

    override fun searchUsers(
        usernameQuery: String,
        pageNumber: Int
    ) = usersRepository.searchUsers(usernameQuery, pageNumber)

    override fun getUserDetails(username: String) = usersRepository.getUserDetails(username)

    override fun getFollowersUserData(username: String) = usersRepository.getFollowersUserData(username)

    override fun getFollowingsUserData(username: String) = usersRepository.getFollowingsUserData(username)

    override suspend fun saveUser(userInfo: UserInfo) = usersRepository.upsertUser(userInfo)

    override fun getSavedUsers() = usersRepository.getSavedUsers()

    override suspend fun deleteUser(userInfo: UserInfo) = usersRepository.deleteUser(userInfo)

}
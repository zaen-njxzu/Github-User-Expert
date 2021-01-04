package com.zaen.githubuser.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zaen.githubuser.core.domain.usecase.UserUseCase

class SearchUsersViewModel(private val userUseCase: UserUseCase): ViewModel() {
    fun searchUsers(usernameQuery: String) = userUseCase.searchUsers(usernameQuery, 1).asLiveData()
}
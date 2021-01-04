package com.zaen.githubuser.searchusers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zaen.githubuser.core.domain.usecase.UserUseCase

class SearchUsersViewModel(val userUseCase: UserUseCase): ViewModel() {
    fun searchUsers(usernameQuery: String) = userUseCase.searchUsers(usernameQuery, 1).asLiveData()
}
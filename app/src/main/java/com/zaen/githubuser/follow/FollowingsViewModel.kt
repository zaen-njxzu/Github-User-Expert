package com.zaen.githubuser.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zaen.githubuser.core.domain.usecase.UserUseCase

class FollowingsViewModel(val userUseCase: UserUseCase): ViewModel() {
    fun getFollowingsData(username: String) = userUseCase.getFollowingsUserData(username).asLiveData()
}
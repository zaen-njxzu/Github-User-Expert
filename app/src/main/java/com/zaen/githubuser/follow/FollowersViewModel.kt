package com.zaen.githubuser.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zaen.githubuser.core.domain.usecase.UserUseCase

class FollowersViewModel(val userUseCase: UserUseCase): ViewModel() {
    fun getFollowersData(username: String) = userUseCase.getFollowersUserData(username).asLiveData()
}
package com.zaen.githubuser.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zaen.githubuser.core.domain.model.UserInfo
import com.zaen.githubuser.core.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class UserDetailsViewModel(private val userUseCase: UserUseCase): ViewModel() {
    fun getUserDetail(username: String) = userUseCase.getUserDetails(username).asLiveData()
    fun saveFavoriteUser(userInfo: UserInfo) = viewModelScope.launch {
        userUseCase.saveUser(userInfo)
    }
}
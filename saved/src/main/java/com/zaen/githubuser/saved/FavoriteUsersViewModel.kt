package com.zaen.githubuser.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zaen.githubuser.core.domain.model.UserInfo
import com.zaen.githubuser.core.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class FavoriteUsersViewModel(private val userUseCase: UserUseCase): ViewModel() {

    val favoriteUsers = userUseCase.getSavedUsers().asLiveData()

    fun deleteUser(userInfo: UserInfo) = viewModelScope.launch {
        userUseCase.deleteUser(userInfo)
    }

    fun saveUser(userInfo: UserInfo) = viewModelScope.launch {
        userUseCase.saveUser(userInfo)
    }
}
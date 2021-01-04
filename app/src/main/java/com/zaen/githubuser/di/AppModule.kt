package com.zaen.githubuser.di

import com.zaen.githubuser.core.domain.usecase.UserInteractor
import com.zaen.githubuser.core.domain.usecase.UserUseCase
import com.zaen.githubuser.follow.FollowersViewModel
import com.zaen.githubuser.follow.FollowingsViewModel
import com.zaen.githubuser.search.SearchUsersViewModel
import com.zaen.githubuser.detail.UserDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

val viewModelModule = module {
    viewModel { SearchUsersViewModel(get()) }
    viewModel { UserDetailsViewModel(get()) }
    viewModel { FollowingsViewModel(get()) }
    viewModel { FollowersViewModel(get()) }
}
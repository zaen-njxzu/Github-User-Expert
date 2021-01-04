package com.zaen.githubuser.saved.di

import com.zaen.githubuser.saved.FavoriteUsersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val savedModule = module {
    viewModel { FavoriteUsersViewModel(get()) }
}
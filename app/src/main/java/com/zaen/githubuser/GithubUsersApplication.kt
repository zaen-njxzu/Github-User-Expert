package com.zaen.githubuser

import android.app.Application
import com.zaen.githubuser.core.di.databaseModule
import com.zaen.githubuser.core.di.networkModule
import com.zaen.githubuser.core.di.repositoryModule
import com.zaen.githubuser.di.useCaseModule
import com.zaen.githubuser.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GithubUsersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@GithubUsersApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}
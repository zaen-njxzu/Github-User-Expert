package com.zaen.githubuser.core.di

import androidx.room.Room
import com.zaen.githubuser.core.BuildConfig
import com.zaen.githubuser.core.data.UserRepository
import com.zaen.githubuser.core.data.source.local.LocalDataSource
import com.zaen.githubuser.core.data.source.local.room.UserInfoDatabase
import com.zaen.githubuser.core.data.source.remote.RemoteDataSource
import com.zaen.githubuser.core.data.source.remote.network.ApiService
import com.zaen.githubuser.core.domain.repository.IUserRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<UserInfoDatabase>().getUserInfoDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.MY_DB_KEY.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            UserInfoDatabase::class.java,
            "UserInfo.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    // factory { AppExecutors() }
    single<IUserRepository> { UserRepository(get(), get()) }
}
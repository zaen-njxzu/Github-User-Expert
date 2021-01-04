package com.zaen.githubuser.core.data.source.local

import com.zaen.githubuser.core.data.source.local.entity.UserInfoEntity
import com.zaen.githubuser.core.data.source.local.room.UserInfoDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val userInfoDao: UserInfoDao) {

    suspend fun upsert(userInfo: UserInfoEntity) = userInfoDao.upsert(userInfo)

    fun getUsersInfo() : Flow<List<UserInfoEntity>> = userInfoDao.getUsersInfo()

    suspend fun delete(userInfo: UserInfoEntity) = userInfoDao.delete(userInfo)

}
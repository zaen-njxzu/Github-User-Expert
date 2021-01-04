package com.zaen.githubuser.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zaen.githubuser.core.data.source.local.entity.UserInfoEntity

@Database(
    entities = [UserInfoEntity::class],
    version = 1
)
abstract class UserInfoDatabase : RoomDatabase() {

    abstract fun getUserInfoDao() : UserInfoDao

}
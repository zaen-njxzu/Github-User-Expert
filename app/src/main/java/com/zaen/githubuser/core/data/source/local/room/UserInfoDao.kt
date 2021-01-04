package com.zaen.githubuser.core.data.source.local.room

import androidx.room.*
import com.zaen.githubuser.core.data.source.local.entity.UserInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(userInfo: UserInfoEntity)

    @Query("SELECT * FROM user_info")
    fun getUsersInfo() : Flow<List<UserInfoEntity>>

    @Delete
    suspend fun delete(userInfo: UserInfoEntity)

}
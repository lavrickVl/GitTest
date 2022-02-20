package com.test.android.gittest.domain.data

import com.test.android.gittest.domain.model.User
import com.test.android.gittest.domain.model.UserDetails
import com.test.android.gittest.util.Resource
import kotlinx.coroutines.flow.Flow

interface GitRepository {

    suspend fun getUsers(): Flow<Resource<List<User>>>

    suspend fun getUserDetails(user: User): Flow<Resource<UserDetails>>
}


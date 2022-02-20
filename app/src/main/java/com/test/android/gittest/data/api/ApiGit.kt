package com.test.android.gittest.data.api

import com.test.android.gittest.data.remote.UserDetailsDTO
import com.test.android.gittest.data.remote.UsersDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiGit {

    @GET("users")
    suspend fun getUsers(): UsersDTO

    @GET("users/{user}")
    suspend fun getUserDetail(@Path("user")user:String): UserDetailsDTO

}
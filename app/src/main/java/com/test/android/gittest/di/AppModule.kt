package com.test.android.gittest.di

import com.test.android.gittest.data.GitRepositoryImpl
import com.test.android.gittest.data.api.ApiGit
import com.test.android.gittest.domain.data.GitRepository
import com.test.android.gittest.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApi(): ApiGit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiGit::class.java)

    @Singleton
    @Provides
    fun provideRepository(api: ApiGit): GitRepository = GitRepositoryImpl(api = api)
}
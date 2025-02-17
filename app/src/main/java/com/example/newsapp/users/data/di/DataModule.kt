package com.example.newsapp.users.data.di

import com.example.newsapp.users.data.remote.services.UserService
import com.example.newsapp.users.data.repositories.UserRepositoryImpl
import com.example.newsapp.users.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUserService(retrofit : Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(service: UserService): UserRepository {
        return UserRepositoryImpl(service)
    }
}
package com.example.newsapp.posts.data.di

import com.example.newsapp.posts.data.remote.services.PostService
import com.example.newsapp.posts.data.repositories.PostRepositoryImpl
import com.example.newsapp.posts.domain.repositories.PostRepository
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
    fun providePostService(retrofit : Retrofit): PostService {
        return retrofit.create(PostService::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(service: PostService): PostRepository {
        return PostRepositoryImpl(service)
    }
}
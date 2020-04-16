package com.worldofplay.app.stories.details.di

import com.worldofplay.app.stories.details.data.remote.StoriesRemoteDataSource
import com.worldofplay.app.stories.details.data.remote.StoriesRepository
import com.worldofplay.app.stories.details.data.remote.StoriesService
import com.worldofplay.app.stories.details.usecases.StoriesUsecase
import com.worldofplay.app.stories.list.data.remote.TopStoriesRemoteDataSource
import com.worldofplay.app.stories.list.data.remote.TopStoriesRepository
import com.worldofplay.app.stories.list.data.remote.TopStoriesService
import com.worldofplay.app.stories.list.usecases.TopStoriesUsecase
import com.worldofplay.core.di.CoreDataModule
import com.worldofplay.core.qualifiers.RetrofitStories
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [CoreDataModule::class])
open class StoriesModule {

    @Singleton
    @Provides
    fun provideStoriesUsecase(repository: StoriesRepository): StoriesUsecase {
        return StoriesUsecase(repository)
    }

    @Singleton
    @Provides
    fun provideStoriesRepository(remoteDataSource: StoriesRemoteDataSource): StoriesRepository {
        return StoriesRepository(remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideStoriesRemoteDataSource(storiesService: StoriesService): StoriesRemoteDataSource {
        return StoriesRemoteDataSource(storiesService)
    }

    @Singleton
    @Provides
    fun provideStoriesService(@RetrofitStories retrofit: Retrofit): StoriesService {
        return retrofit.create<StoriesService>(StoriesService::class.java)
    }

}
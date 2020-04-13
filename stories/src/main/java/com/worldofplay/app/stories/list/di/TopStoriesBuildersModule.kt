package com.worldofplay.app.stories.list.di

import com.worldofplay.app.stories.list.presentation.TopStoriesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TopStoriesBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeTopStoriesFragment(): TopStoriesFragment
}
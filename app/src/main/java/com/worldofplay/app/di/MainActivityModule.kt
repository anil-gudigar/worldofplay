package com.worldofplay.app.di

import com.worldofplay.app.ui.MainActivity
import com.worldofplay.app.stories.list.di.TopStoriesBuildersModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [TopStoriesBuildersModule::class,TopStoriesBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
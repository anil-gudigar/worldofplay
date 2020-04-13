package com.worldofplay.app.di

import androidx.lifecycle.ViewModelProvider
import com.worldofplay.app.stories.list.di.TopStoriesViewModelModule
import com.worldofplay.core.di.ViewModelFactory

import dagger.Binds
import dagger.Module

@Module(includes = [TopStoriesViewModelModule::class])
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory( factory: ViewModelFactory):
            ViewModelProvider.Factory
}

package com.worldofplay.app.di

import com.worldofplay.app.stories.details.di.StoriesModule
import com.worldofplay.app.stories.list.di.TopStoriesModule
import com.worldofplay.core.di.CoreDataModule
import dagger.Module

@Module(
    includes = [ViewModelModule::class, CoreDataModule::class, TopStoriesModule::class,StoriesModule::class]
)
class AppModule
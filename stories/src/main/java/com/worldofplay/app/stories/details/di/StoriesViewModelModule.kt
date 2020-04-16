package com.worldofplay.app.stories.details.di

import androidx.lifecycle.ViewModel
import com.worldofplay.app.stories.list.viewmodel.TopStoriesViewModel
import com.worldofplay.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StoriesViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TopStoriesViewModel::class)
    abstract fun bindTopStoriesViewModel(viewModel: TopStoriesViewModel): ViewModel
}
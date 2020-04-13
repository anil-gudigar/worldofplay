package com.worldofplay.app.stories.list.di

import androidx.lifecycle.ViewModel
import com.worldofplay.app.stories.list.viewmodel.TopStoriesViewModel
import com.worldofplay.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TopStoriesViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TopStoriesViewModel::class)
    abstract fun bindTopStoriesViewModel(viewModel: TopStoriesViewModel): ViewModel
}
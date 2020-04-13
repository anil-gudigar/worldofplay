package com.worldofplay.app.stories.list.usecases

import androidx.lifecycle.LiveData
import com.worldofplay.app.stories.list.data.remote.TopStoriesRepository
import com.worldofplay.app.stories.list.domain.TopStories
import com.worldofplay.core.data.Result
import javax.inject.Inject

class TopStoriesUsecase @Inject constructor(val repository: TopStoriesRepository){

    fun getTopStories(): LiveData<Result<TopStories>> {
        return repository.topStories()
    }

}
package com.worldofplay.app.stories.details.usecases

import androidx.lifecycle.LiveData
import com.worldofplay.app.stories.details.data.remote.StoriesRepository
import com.worldofplay.app.stories.details.domain.StoriesResponse
import com.worldofplay.core.data.Result
import javax.inject.Inject

class StoriesUsecase @Inject constructor(val repository: StoriesRepository){

    fun getTopStories(story_id:String): LiveData<Result<StoriesResponse>> {
        return repository.getStories(story_id)
    }

}
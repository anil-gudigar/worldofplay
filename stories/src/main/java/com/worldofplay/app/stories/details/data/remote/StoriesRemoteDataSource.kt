package com.worldofplay.app.stories.details.data.remote

import com.worldofplay.core.api.BaseDataSource
import javax.inject.Inject

class StoriesRemoteDataSource @Inject constructor(private val service: StoriesService) : BaseDataSource() {

    suspend fun fetchData(story_id:String) = getResult { service.getStories(story_id) }

}
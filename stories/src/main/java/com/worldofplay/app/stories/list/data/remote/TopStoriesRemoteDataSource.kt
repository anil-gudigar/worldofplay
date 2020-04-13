package com.worldofplay.app.stories.list.data.remote

import com.worldofplay.core.api.BaseDataSource
import javax.inject.Inject

class TopStoriesRemoteDataSource @Inject constructor(private val service: TopStoriesService) : BaseDataSource() {

    suspend fun fetchData() = getResult { service.getTopStories() }

}
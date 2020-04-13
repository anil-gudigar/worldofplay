package com.worldofplay.app.stories.list.data.remote

import androidx.lifecycle.LiveData
import com.worldofplay.app.stories.list.domain.TopStories
import com.worldofplay.core.data.Result
import com.worldofplay.core.data.resultLiveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class TopStoriesRepository @Inject constructor(private val remoteSource:TopStoriesRemoteDataSource) {

    fun topStories(): LiveData<Result<TopStories>> {
       return resultLiveData( networkCall = { remoteSource.fetchData() })
    }

}
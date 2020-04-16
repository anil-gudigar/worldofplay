package com.worldofplay.app.stories.details.data.remote

import androidx.lifecycle.LiveData
import com.worldofplay.app.stories.details.domain.StoriesResponse
import com.worldofplay.core.data.Result
import com.worldofplay.core.data.resultLiveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class StoriesRepository @Inject constructor(private val remoteSource: StoriesRemoteDataSource) {

    fun getStories(story_id:String): LiveData<Result<StoriesResponse>> {
        return resultLiveData( networkCall = { remoteSource.fetchData(story_id) })
    }

}
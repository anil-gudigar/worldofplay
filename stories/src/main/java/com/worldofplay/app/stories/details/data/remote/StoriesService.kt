package com.worldofplay.app.stories.details.data.remote

import com.worldofplay.app.stories.details.domain.StoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StoriesService {
        @GET("v0/item/{story_id}")
        suspend fun getStories(@Path("story_id") story_id:String): Response<StoriesResponse>
}
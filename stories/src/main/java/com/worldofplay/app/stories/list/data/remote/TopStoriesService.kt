package com.worldofplay.app.stories.list.data.remote

import com.worldofplay.app.stories.list.domain.TopStories
import retrofit2.Response
import retrofit2.http.GET

interface TopStoriesService{
    @GET("v0/topstories.json")
    suspend fun getTopStories(): Response<TopStories>
}
package com.worldofplay.app.stories.details.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.worldofplay.app.stories.details.domain.StoriesResponse
import com.worldofplay.app.stories.details.usecases.StoriesUsecase
import com.worldofplay.core.data.Result
import com.worldofplay.core.data.SingleLiveEvent
import javax.inject.Inject


class StoriesViewModel @Inject constructor(application: Application) : ViewModel() {
    @Inject
    lateinit var storiesUsecase: StoriesUsecase
    val errorMessageData: SingleLiveEvent<String> = SingleLiveEvent()
    val loadingData: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val successData: SingleLiveEvent<StoriesResponse> = SingleLiveEvent()


    fun getStories(lifecycleOwner: LifecycleOwner,story_id: String) {
        storiesUsecase.getTopStories(story_id).observe(lifecycleOwner,Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    loadingData.postValue(false)
                    result.data?.let {
                        successData.postValue(it)
                    }
                }
                Result.Status.LOADING -> {
                    loadingData.postValue(true)
                }
                Result.Status.ERROR -> {
                    loadingData.postValue(false)
                    errorMessageData.postValue(result.message)
                }
            }
        })
    }

    fun getAllStories(lifecycleOwner: LifecycleOwner,topStories: MutableList<String>) {
        for (story_id in topStories){
            getStories(lifecycleOwner,story_id+EXT_JSON)
        }
    }
    companion object{
        const val EXT_JSON=".json"
    }
}


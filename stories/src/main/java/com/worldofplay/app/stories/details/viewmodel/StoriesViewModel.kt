package com.worldofplay.app.stories.details.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.worldofplay.app.stories.details.domain.StoriesResponse
import com.worldofplay.app.stories.details.usecases.StoriesUsecase
import com.worldofplay.core.data.Result
import javax.inject.Inject


class StoriesViewModel @Inject constructor(application: Application) : ViewModel() {
    @Inject
    lateinit var storiesUsecase: StoriesUsecase
    val errorMessageData: MutableLiveData<String> = MutableLiveData()
    val loadingData: MutableLiveData<Boolean> = MutableLiveData()
    val successData: MutableLiveData<StoriesResponse> = MutableLiveData()

    fun getStories(story_id: String) {
        Log.i("Anil", "getStories :" + story_id)
        storiesUsecase.getTopStories(story_id).observeForever(Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    loadingData.postValue(false)
                    result.data?.let {
                        Log.i("Anil", "Result :" + it.toString())
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

    fun getAllStories(topStories: MutableList<String>) {
        for (story_id in topStories){
            getStories(story_id+".json")
        }
    }
}


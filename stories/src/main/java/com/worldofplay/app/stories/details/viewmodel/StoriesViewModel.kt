package com.worldofplay.app.stories.details.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.worldofplay.app.stories.details.domain.StoriesResponse
import com.worldofplay.app.stories.details.usecases.StoriesUsecase
import com.worldofplay.core.data.Result
import java.util.*
import javax.inject.Inject


class StoriesViewModel @Inject constructor(application: Application) : ViewModel() {
    @Inject
    lateinit var storiesUsecase: StoriesUsecase
    val errorMessageData: MutableLiveData<String> = MutableLiveData()
    val loadingData: MutableLiveData<Boolean> = MutableLiveData()
    val successData: MutableLiveData<StoriesResponse> = MutableLiveData()
    val queue = LinkedList<String>()

    fun getStories(story_id:String) {
        storiesUsecase.getTopStories(story_id).observeForever(Observer { result ->
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

}


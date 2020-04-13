package com.worldofplay.app.stories.list.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.worldofplay.app.stories.list.domain.TopStories
import com.worldofplay.app.stories.list.usecases.TopStoriesUsecase
import com.worldofplay.core.data.Result
import javax.inject.Inject

class TopStoriesViewModel@Inject constructor(application: Application) : ViewModel() {
    @Inject
    lateinit var topStoriesUsecase: TopStoriesUsecase

    val errorMessageData: MutableLiveData<String> = MutableLiveData()
    val loadingData: MutableLiveData<Boolean> = MutableLiveData()
    val successData: MutableLiveData<TopStories> = MutableLiveData()

    fun getTopStories() {
        topStoriesUsecase.getTopStories().observeForever( Observer { result ->
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
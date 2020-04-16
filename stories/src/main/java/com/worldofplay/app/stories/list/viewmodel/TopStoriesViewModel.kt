package com.worldofplay.app.stories.list.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.worldofplay.app.stories.details.domain.StoriesResponse
import com.worldofplay.app.stories.list.domain.TopStories
import com.worldofplay.app.stories.list.presentation.callbacks.PaginationListener.Companion.PAGE_SIZE
import com.worldofplay.app.stories.list.presentation.callbacks.PaginationListener.Companion.PAGE_START
import com.worldofplay.app.stories.list.usecases.TopStoriesUsecase
import com.worldofplay.core.data.Result
import com.worldofplay.core.data.SingleLiveEvent
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class TopStoriesViewModel @Inject constructor(application: Application) : ViewModel() {
    @Inject
    lateinit var topStoriesUsecase: TopStoriesUsecase

    var currentPage: Int = PAGE_START
    var isLastPage = false
    var totalPage = 10
    var isLoading = false
    var itemCount = 0
    val errorMessageData: SingleLiveEvent<String> = SingleLiveEvent()
    val loadingData: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val successData: SingleLiveEvent<TopStories> = SingleLiveEvent()
    val allStories: SingleLiveEvent<MutableList<StoriesResponse>> = SingleLiveEvent()

    fun getTopStories(lifecycleOwner: LifecycleOwner) {
        allStories.value = ArrayList()
        topStoriesUsecase.getTopStories().observe(lifecycleOwner,Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    loadingData.postValue(false)
                    result.data?.let {
                        itemCount = it.size
                        totalPage = itemCount / PAGE_SIZE
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

    fun getNextPageList(): MutableList<String>? {
        val currentIndex =PAGE_SIZE * (currentPage - 1)
        val nextIndex = currentPage * PAGE_SIZE
        if (currentPage == totalPage){
            isLastPage = true
        }
        return successData.value?.subList(currentIndex,nextIndex)
    }
}
package com.worldofplay.app.stories.details.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.worldofplay.app.stories.details.data.worker.StoriesDataWorker
import com.worldofplay.app.stories.details.domain.StoriesResponse
import com.worldofplay.app.stories.details.usecases.StoriesUsecase
import com.worldofplay.core.data.Result
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class StoriesViewModel @Inject constructor(application: Application) : ViewModel() {
    @Inject
    lateinit var storiesUsecase: StoriesUsecase
    val errorMessageData: MutableLiveData<String> = MutableLiveData()
    val loadingData: MutableLiveData<Boolean> = MutableLiveData()
    val successData: MutableLiveData<MutableList<StoriesResponse>> = MutableLiveData()
    val storiesQueueUpdated: MutableLiveData<Boolean> = MutableLiveData()
    val queue = ConcurrentLinkedDeque<String>()
    var taskEnqued: Boolean = false

    fun getStories(story_id: String) {
        storiesUsecase.getTopStories(story_id).observeForever(Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    loadingData.postValue(false)
                    result.data?.let {
                        Log.i("Anil", "Result :" + it.toString())
                        successData.value?.add(it)
                        queue.remove(story_id)
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

    fun getAllStories(context: Context) {
        Log.i("Anil", " queue : " + queue.size)
        startWorker(context)
        /* while (!queue.isEmpty()){
             taskEnqued = true

             Log.i("Anil"," story_id : "+story_id)
             getStories(story_id)
         }
         taskEnqued = false*/
    }

    fun startWorker(context: Context) {
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val data = Data.Builder()
        val story_id = queue.peek()
        Log.i("Anil", " story_id : " + story_id)
        data.putString("story_id", story_id)
        val request: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<StoriesDataWorker>()
                .setConstraints(constraints)
                .addTag("GET_STORY")
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.SECONDS)
                .setInputData(data.build())
                .build()
        WorkManager.getInstance(context)
            .beginUniqueWork(StoriesDataWorker.TAG, ExistingWorkPolicy.APPEND, request)
            .enqueue()
    }
}


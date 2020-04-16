package com.worldofplay.app.stories.details.data.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.worldofplay.app.stories.details.usecases.StoriesUsecase
import com.worldofplay.core.BaseApp
import javax.inject.Inject

class StoriesDataWorker @Inject constructor(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    companion object {
        val TAG = StoriesDataWorker::class.java.simpleName
    }
    @Inject
    lateinit var storiesUsecase: StoriesUsecase

    override fun doWork(): Result {
        Log.i(TAG, "Fetching Data from Remote host")
        val applicationContext: Context = getApplicationContext()
        //(applicationContext as BaseApp).DaggerAppComponent.inject(this)
        //get Input Data back using "inputData" variable
        val story_id =  inputData.getString("story_id")
        return try {
            story_id?.let { storiesUsecase.getTopStories(it).observeForever(Observer { result ->
                when (result.status) {
                    com.worldofplay.core.data.Result.Status.SUCCESS -> {
                        result.data?.let {
                            Result.success()
                        }
                    }
                    com.worldofplay.core.data.Result.Status.LOADING -> {
                        Result.failure()
                    }
                    com.worldofplay.core.data.Result.Status.ERROR -> {
                        Result.failure()
                    }
                }
            }) }
        } catch (e: Throwable) {
            Result.failure()
        } as Result
    }
}
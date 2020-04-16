package com.worldofplay.app.stories.details.data.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.worldofplay.app.stories.details.usecases.StoriesUsecase

class StoriesDataWorker constructor(
    val storiesUsecase: StoriesUsecase,
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {
    companion object {
        val TAG = StoriesDataWorker::class.java.simpleName
    }

    override fun doWork(): Result {
        Log.i("Anil", "Fetching Data from Remote host")
        val story_id = inputData.getString("story_id")
        story_id?.let {
            storiesUsecase.getTopStories(it).observeForever(Observer { result ->
                when (result.status) {
                    com.worldofplay.core.data.Result.Status.SUCCESS -> {
                        result.data?.let {
                            Log.i(TAG, "Data:" + it.toString())
                            return@Observer
                        }
                    }
                    com.worldofplay.core.data.Result.Status.LOADING -> {
                        Log.i("Anil", "Failed")
                        return@Observer
                    }
                    com.worldofplay.core.data.Result.Status.ERROR -> {
                        Log.i("Anil", "ERROR")
                        return@Observer
                    }
                }
            })
        }
        return  Result.success()
    }
}
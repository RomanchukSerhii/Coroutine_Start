package com.example.coroutinestart

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)

    fun method() {
        val childJob1 = coroutineScope.launch {
            delay(3000)
            Log.d(LOG_TAG, "first coroutine is finished" )
        }
        val childJob2 = coroutineScope.launch {
            delay(2000)
            Log.d(LOG_TAG, "second coroutine is finished")
        }
        thread {
            Thread.sleep(1000)
            parentJob.cancel()
            Log.d(LOG_TAG, "Parent job active: ${parentJob.isActive}")
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    companion object {
        private const val LOG_TAG = "MainViewModel"
    }
}
package com.zedan.newsapp.domain.usecase

import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

abstract class UseCase<T, R>{

    protected abstract suspend fun doWork(param : T, resultAction : suspend (result : R)->Unit )

    suspend fun work(param : T, actionResult : suspend (result : R)->Unit){
        withContext(Default){ doWork(param, actionResult) }
    }

    class None
}
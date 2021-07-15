package com.soyvictorherrera.nosedive.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<T> {

    protected abstract suspend fun buildFlow(): Flow<T>

    protected fun getDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    suspend fun execute(action: (value: T) -> Unit) {
        buildFlow().flowOn(getDispatcher())
            .collect { value ->
                action(value)
            }
    }

}

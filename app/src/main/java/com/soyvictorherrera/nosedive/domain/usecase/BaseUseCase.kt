package com.soyvictorherrera.nosedive.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<T> {

    protected open val dispatcher: CoroutineDispatcher = Dispatchers.IO

    protected abstract suspend fun buildFlow(): Flow<T>

    suspend fun execute(action: (value: T) -> Unit) {
        buildFlow().flowOn(dispatcher)
            .collect { value ->
                action(value)
            }
    }

}

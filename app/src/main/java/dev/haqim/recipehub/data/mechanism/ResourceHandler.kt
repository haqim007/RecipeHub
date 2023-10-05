package dev.haqim.recipehub.data.mechanism

import kotlinx.coroutines.flow.Flow


fun <T> Resource<T>.handle(handler: ResourceHandler<T>){
    when (this) {
        is Resource.Success -> handler.onSuccess(this.data)
        is Resource.Error -> handler.onError()
        is Resource.Loading -> handler.onLoading()
        else -> handler.onIdle()
    }
}

interface ResourceHandler<T> {
    fun onSuccess(data: T?)
    fun onError()
    fun onLoading()
    fun onIdle()
}

suspend fun <T> Flow<Resource<T>>.handleCollect(
    onSuccess: (resource: Resource<T>) -> Unit,
    onError: (resource: Resource<T>) -> Unit = {},
    onLoading: (resource: Resource<T>) -> Unit = {},
    onIdle: () -> Unit = {}
){
    this.collect{ it.handle(object: ResourceHandler<T>{
        override fun onSuccess(data: T?) {
            onSuccess(it)
        }

        override fun onError() {
            onError(it)
        }

        override fun onLoading() {
            onLoading(it)
        }

        override fun onIdle() {
            onIdle
        }
    }) }
}

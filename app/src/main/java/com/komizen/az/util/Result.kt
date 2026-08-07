package com.komizen.az.util

sealed class KomizenResult<out T> {
    data class Success<T>(val data: T) : KomizenResult<T>()
    data class Error(val exception: Throwable) : KomizenResult<Nothing>()
    data object Loading : KomizenResult<Nothing>()
}

inline fun <T> KomizenResult<T>.onSuccess(action: (T) -> Unit): KomizenResult<T> {
    if (this is KomizenResult.Success) action(data)
    return this
}

inline fun <T> KomizenResult<T>.onError(action: (Throwable) -> Unit): KomizenResult<T> {
    if (this is KomizenResult.Error) action(exception)
    return this
}

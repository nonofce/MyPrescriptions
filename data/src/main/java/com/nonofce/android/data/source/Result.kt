package com.nonofce.android.data.source

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    object Empty: Result<Nothing>()
    object Error : Result<Nothing>()
}

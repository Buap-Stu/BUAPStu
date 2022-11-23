package com.buap.stu.buapstu.core.utils

sealed class Resource<out T> {
    object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    object Failure: Resource<Nothing>()
}
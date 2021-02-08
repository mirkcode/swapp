package com.mvaresedev.swapp.utils

sealed class ResultWrapper<out T>(val result: T) {
    class Success<out T>(result: T): ResultWrapper<T>(result)
    class Error<out T>(result: T): ResultWrapper<T>(result)
}
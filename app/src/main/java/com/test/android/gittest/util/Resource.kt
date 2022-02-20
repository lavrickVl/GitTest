package com.test.android.gittest.util

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(val data: T? = null, val message: String? = null, loading: Boolean = false) {
    class Loading<T>(data: T? = null, loading:Boolean = true): Resource<T>(data)
    class Success<T>(data: T?, loading:Boolean = false): Resource<T>(data)
    class Error<T>(message: String, data: T? = null, loading:Boolean = false): Resource<T>(data, message)
}
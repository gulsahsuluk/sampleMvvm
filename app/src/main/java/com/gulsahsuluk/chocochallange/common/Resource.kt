package com.gulsahsuluk.chocochallange.common

import androidx.annotation.NonNull

/**
 * reference : https://developer.android.com/jetpack/docs/guide#addendum
 */
class Resource<T> constructor(val state: ResourceState, var data: T?, val error: AppError? = null) {

    companion object {

        fun <T> success(@NonNull data: T): Resource<T> {
            return Resource(ResourceState.SUCCESS, data)
        }

        fun <T> error(error: AppError): Resource<T> {
            return Resource(state = ResourceState.ERROR, data = null, error = error)
        }

        fun <T> loading(): Resource<T> = Resource(ResourceState.LOADING, null)
    }
}
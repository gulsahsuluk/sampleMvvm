package com.gulsahsuluk.chocochallange.common

/**
 * Helper class to create error type.
 */

sealed class AppError {
    object Network : AppError()
    object Unexpected : AppError()
    data class Custom(val message: Int): AppError()
}
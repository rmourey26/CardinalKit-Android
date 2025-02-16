package io.quantumone.cardinalkit.domain.models

sealed class Response<out T> {
    object Loading : Response<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : Response<T>()

    data class Error(
        val e: Exception?
    ) : Response<Nothing>()
}

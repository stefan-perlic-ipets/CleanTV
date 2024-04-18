package com.perla.cleantv.domain

sealed class Result<out A, out B> {
    class Success<A>(val value: A) : Result<A, Nothing>()
    class Error<B>(val value: B) : Result<Nothing, B>()
}

fun <T, E, R> Result<T, E>.fold(
    onSuccess: (T) -> R,
    onError: (E) -> R
): R {
    return when (this) {
        is Result.Success -> onSuccess(value)
        is Result.Error -> onError(value)
    }
}
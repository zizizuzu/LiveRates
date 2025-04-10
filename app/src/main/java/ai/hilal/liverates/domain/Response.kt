package ai.hilal.liverates.domain

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val exception: Throwable) : Response<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun error(exception: Throwable) = Error(exception)
    }
}

inline fun <T> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    if (this is Response.Success) action(data)
    return this
}

inline fun <T> Response<T>.onError(action: (Throwable) -> Unit): Response<T> {
    if (this is Response.Error) action(exception)
    return this
}
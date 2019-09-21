package sample.base.ui

import androidx.annotation.StringRes

/**
 * A base sealed class that represents a result of an operation, one of [Value], [Error] or [Complete].
 */
sealed class Result<out T> {

    companion object {
        fun <T> start(): Result<T> = Start()
        fun <T> value(value: T): Result<T> = Value(value)
        fun <T> error(@StringRes message: Int): Result<T> = Error(message)
        fun <T> complete(): Result<T> = Complete()
    }
}

/**
 * Represents a starting of an operation.
 */
class Start<out T> : Result<T>()

/**
 * Represents a value as result of an operation.
 */
data class Value<out T>(val value: T) : Result<T>()

/**
 * Represents an error as result of an operation.
 */
data class Error<out T>(@StringRes val message: Int) : Result<T>()

/**
 * Represents a completion of an operation.
 */
class Complete<out T> : Result<T>()
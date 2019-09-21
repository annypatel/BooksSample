package sample.base.domain

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * A use case that takes an argument and returns a result.
 */
interface UseCase<in I, out R> {

    operator fun invoke(input: I): R
}

/**
 * Invoke extension method on [UseCase] when there is no input parameter.
 */
operator fun <R> UseCase<Unit, R>.invoke() = invoke(Unit)

/** A Use Case that returns a result as Completable. */
interface CompletableUseCase<I> : UseCase<I, Completable>

/** A Use Case that returns a result as Single. */
interface SingleUseCase<I, R> : UseCase<I, Single<R>>

/** A Use Case that returns a result as Flowable. */
interface FlowableUseCase<I, R> : UseCase<I, Flowable<R>>
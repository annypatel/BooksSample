package sample.base.domain

import io.reactivex.Scheduler

/**
 * Just a wrapper for rx-java schedulers.
 */
class RxSchedulers(
    val io: Scheduler,
    val computation: Scheduler,
    val database: Scheduler,
    val main: Scheduler
)
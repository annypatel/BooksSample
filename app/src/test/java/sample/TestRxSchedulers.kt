package sample

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import sample.base.domain.RxSchedulers

/**
 * [RxSchedulers] for local unit tests that uses [Schedulers.trampoline] scheduler.
 */
fun testRxSchedulers(
    io: Scheduler = Schedulers.trampoline(),
    computation: Scheduler = Schedulers.trampoline(),
    database: Scheduler = Schedulers.trampoline(),
    main: Scheduler = Schedulers.trampoline()
) = RxSchedulers(io, computation, database, main)
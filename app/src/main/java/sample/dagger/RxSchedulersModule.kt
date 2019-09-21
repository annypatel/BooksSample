package sample.dagger

import sample.base.domain.RxSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Module for injecting [RxSchedulers] into application component.
 */
@Module
object RxSchedulersModule {

    @Provides
    @Singleton
    @JvmStatic
    fun providesRxSchedulers() = RxSchedulers(
        io = Schedulers.io(),
        computation = Schedulers.computation(),
        database = Schedulers.single(),
        main = AndroidSchedulers.mainThread()
    )
}
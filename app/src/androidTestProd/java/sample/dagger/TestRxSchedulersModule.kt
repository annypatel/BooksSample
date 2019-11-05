package sample.dagger

import sample.base.domain.RxSchedulers
import com.squareup.rx2.idler.Rx2Idler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Module that wraps schedulers with idling resource using RxIdler before injecting [RxSchedulers] into test application
 * component.
 */
@Module
object TestRxSchedulersModule {

    private val schedulers: RxSchedulers

    init {
        RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create("io"))
        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("computation"))
        RxJavaPlugins.setInitSingleSchedulerHandler(Rx2Idler.create("database"))

        schedulers = RxSchedulers(
            io = Schedulers.io(),
            computation = Schedulers.computation(),
            database = Schedulers.single(),
            main = AndroidSchedulers.mainThread()
        )
    }

    @Provides
    @Singleton
    @JvmStatic
    fun providesRxSchedulers() = schedulers
}
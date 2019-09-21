package sample.books.domain

import sample.base.domain.RxSchedulers
import sample.base.domain.SingleUseCase
import io.reactivex.Single
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import org.threeten.bp.temporal.TemporalField
import org.threeten.bp.temporal.WeekFields
import java.util.Locale
import javax.inject.Inject

/**
 * Use case for sort books by first day of the week.
 */
interface SortBooksByWeekUseCase : SingleUseCase<List<Book>, List<BookGroup>>

/**
 * Implementation of [SortBooksByWeekUseCase].
 */
class SortBooksByWeekUseCaseImpl @Inject constructor(
    private val schedulers: RxSchedulers
) : SortBooksByWeekUseCase {

    override fun invoke(input: List<Book>): Single<List<BookGroup>> {
        return Single.just(input)
            .subscribeOn(schedulers.computation)
            .map { books ->
                books.sortedByDescending { it.publishDate }
                    .groupBy { firstDayOfWeek(it.publishDate) }
                    .map { BookGroup(it.key, it.value) }
            }
    }

    private val dayOfWeek: TemporalField = WeekFields.of(Locale.getDefault()).dayOfWeek()
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    private fun firstDayOfWeek(date: LocalDate): String {
        return date.with(dayOfWeek, 1)
            .format(dateFormatter)
    }
}
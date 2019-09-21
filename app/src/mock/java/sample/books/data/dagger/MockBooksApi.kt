package sample.books.data.dagger

import io.reactivex.Single
import org.threeten.bp.LocalDate
import sample.base.domain.RxSchedulers
import sample.books.data.network.BooksApi
import sample.books.domain.Book
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MockBooksApi @Inject constructor(
    private val schedulers: RxSchedulers
) : BooksApi {

    override fun getAllBooks(): Single<List<Book>> = Single.just(
        listOf(
            Book(
                "d241b2b",
                "Eat, Move, Sleep",
                "Tom Rath",
                LocalDate.of(2018, 7, 3),
                "https://i.ibb.co/jbDph5k/5694b3802f6827000700002a.jpg"
            ),
            Book(
                "eea5ee1",
                "The Secret Life of Sleep",
                "Kat Duff",
                LocalDate.of(2018, 7, 2),
                "https://i.ibb.co/6FJw6B5/56c1de12587c820007000063.jpg"
            ),
            Book(
                "7e2401d",
                "The Sleep Revolution",
                "Arianna Huffington",
                LocalDate.of(2018, 6, 19),
                "https://i.ibb.co/2ZMYJgn/5776159b86883200034f4744.jpg"
            ),
            Book(
                "03779ee",
                "Real Artists Don’t Starve",
                "Jeff Goins",
                LocalDate.of(2017, 12, 31),
                "https://i.ibb.co/bX7G7Fr/599817dbb238e10006a125cb.jpg"
            ),
            Book(
                "e021f6c",
                "Hirntuning",
                "Dave Asprey",
                LocalDate.of(2018, 1, 1),
                "https://i.ibb.co/fH4XxV5/5b284d46b238e1000787b43d.jpg"
            ),
            Book(
                "8722651",
                "The Red Queen",
                "Matt Ridley",
                LocalDate.of(2018, 6, 17),
                "https://i.ibb.co/q54ZzBy/58eb3b45a54bbb000464bab8.jpg"
            ),
            Book(
                "2cb8609",
                "Inner Engineering",
                "Sadhguru Jaggi Vasudev",
                LocalDate.of(2018, 6, 18),
                "https://i.ibb.co/wL2jFjv/59751e00b238e100057032bf.jpg"
            ),
            Book(
                "b4388e4",
                "Feathers",
                "Thor Hanson",
                LocalDate.of(2018, 6, 18),
                "https://i.ibb.co/7RKf1mN/59773cc1b238e10005084241.jpg"
            ),
            Book(
                "1cdb347",
                "The Subtle Art of Not Giving a F*ck",
                "Mark Manson",
                LocalDate.of(2016, 7, 2),
                "https://i.ibb.co/CbLhZXJ/592933bbb238e10007b6b0a5.jpg"
            ),
            Book(
                "a597717",
                "Bringing Up Bébé",
                "Pamela Druckerman",
                LocalDate.of(2016, 7, 3),
                "https://i.ibb.co/DwPKn5D/57e6c3f0afd7bf0003b7052d.jpg"
            ),
            Book(
                "99c1c39",
                "A Book With a Very Long Title, Veeeeeeeeeeeeeeeeery Long",
                "The Android Team",
                LocalDate.of(2014, 1, 1),
                "https://i.ibb.co/8Xp6M1p/5575979e3935610007420000.jpg"
            )
        )
    ).delay(2, TimeUnit.SECONDS, schedulers.computation)
}

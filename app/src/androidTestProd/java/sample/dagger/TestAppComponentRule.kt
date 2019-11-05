package sample.dagger

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import sample.App
import sample.books.data.network.BooksApi
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * JUnit rule to create the [TestAppComponent] and inject the [App] into it.
 */
class TestAppComponentRule(
    private val booksApi: BooksApi
) : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {

            override fun evaluate() {

                val app = getApplicationContext<App>()
                DaggerTestAppComponent.factory()
                    .create(app, booksApi)
                    .inject(app)

                base.evaluate()
            }
        }
    }
}

package sample.books.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import sample.books.R
import sample.base.ui.observe
import sample.books.domain.BookListItem
import sample.books.domain.SortBy
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_books.*
import javax.inject.Inject

class BooksActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<BooksViewModel> { factory }
    private lateinit var recyclerAdapter: BookListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = BookListRecyclerAdapter()
        recyclerView.adapter = recyclerAdapter

        viewModel.books.observe(
            this,
            ::showLoading,
            ::showBooks,
            ::showErrorLoadingData
        )

        swipeRefreshView.setColorSchemeResources(R.color.accent)
        swipeRefreshView.setOnRefreshListener { viewModel.refreshBooks() }
    }

    private fun showLoading() {
        swipeRefreshView.isRefreshing = true
    }

    private fun showBooks(items: List<BookListItem>) {
        recyclerAdapter.setItems(items)
        recyclerAdapter.notifyDataSetChanged()
        swipeRefreshView.isRefreshing = false
    }

    private fun showErrorLoadingData(@StringRes message: Int) {
        Snackbar.make(swipeRefreshView, message, Snackbar.LENGTH_LONG).show()
        swipeRefreshView.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_books_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sortByMenu) {
            showSortByMenu()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSortByMenu() {
        val menu = PopupMenu(this, findViewById(R.id.sortByMenu))
        menu.inflate(R.menu.menu_sort_by_options)
        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sortByNameMenu -> viewModel.sortBooksBy(SortBy.Name)
                R.id.sortByWeekMenu -> viewModel.sortBooksBy(SortBy.Week)
            }
            true
        }
        menu.show()
    }
}

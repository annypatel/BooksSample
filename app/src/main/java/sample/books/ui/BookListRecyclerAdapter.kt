package sample.books.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sample.books.R
import sample.books.domain.BookListItem
import sample.books.domain.BookListItem.BookItem
import sample.books.domain.BookListItem.HeaderItem
import sample.books.ui.BookListRecyclerAdapter.ItemViewHolder
import sample.books.ui.BookListRecyclerAdapter.ItemViewHolder.BookItemViewHolder
import sample.books.ui.BookListRecyclerAdapter.ItemViewHolder.HeaderItemViewHolder
import com.squareup.picasso.Picasso
import java.util.ArrayList

/**
 * Adapter for books recycler view.
 */
class BookListRecyclerAdapter : RecyclerView.Adapter<ItemViewHolder<BookListItem>>() {

    private val items = ArrayList<BookListItem>()

    fun setItems(items: List<BookListItem>) {
        this.items.clear()
        this.items.addAll(items)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HeaderItem -> R.layout.header_list_item
            is BookItem -> R.layout.book_list_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<BookListItem> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        @Suppress("UNCHECKED_CAST")
        return when (viewType) {
            R.layout.header_list_item -> HeaderItemViewHolder(view)
            R.layout.book_list_item -> BookItemViewHolder(view)
            else -> throw RuntimeException("Unknown view type")
        } as ItemViewHolder<BookListItem>
    }

    override fun onBindViewHolder(holder: ItemViewHolder<BookListItem>, position: Int) {
        holder.bind(items[position])
    }

    /**
     * Base view holder for books recycler view adapter.
     */
    sealed class ItemViewHolder<in T : BookListItem>(view: View) : RecyclerView.ViewHolder(view) {

        /**
         * To bind the model item with the view.
         */
        abstract fun bind(item: T)

        /** View holder for header item view. */
        class HeaderItemViewHolder(view: View) : ItemViewHolder<HeaderItem>(view) {

            private var headerTextView: TextView = view.findViewById(R.id.headerTextView)

            override fun bind(item: HeaderItem) {
                headerTextView.text = item.name
            }
        }

        /** View holder for book item view. */
        class BookItemViewHolder(view: View) : ItemViewHolder<BookItem>(view) {

            private var authorTextView: TextView = view.findViewById(R.id.authorTextView)
            private var titleTextView: TextView = view.findViewById(R.id.titleTextView)
            private var coverImageView: ImageView = view.findViewById(R.id.coverImageView)

            override fun bind(item: BookItem) {
                titleTextView.text = item.book.name
                authorTextView.text = item.book.author
                Picasso.get()
                    .load(item.book.coverImageUrl)
                    .into(coverImageView)
            }
        }
    }
}

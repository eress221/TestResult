package com.eress.testresult.viewholder

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_book_holder.view.*
import com.bumptech.glide.Glide
import com.eress.testresult.R
import com.eress.testresult.activity.DetailActivity
import com.eress.testresult.model.Book

class BookViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {
            Intent(it.context, DetailActivity::class.java).let {
                it.putExtra("isbn13", view.tv_title.tag.toString())
                view.context.startActivity(it)
            }
        }
    }

    fun bind(book: Book) {
        Glide.with(view).load(book.image).into(view.iv_thumbnail)
        view.tv_title.tag = book.isbn13
        view.tv_title.text = book.title
        view.tv_subtitle.text = book.subtitle

    }

    companion object {
        fun create(parent: ViewGroup): BookViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_holder, parent, false)
            return BookViewHolder(view)
        }
    }
}
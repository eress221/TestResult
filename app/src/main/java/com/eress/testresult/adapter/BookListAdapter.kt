package com.eress.testresult.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eress.testresult.viewholder.BookViewHolder
import com.eress.testresult.model.Book

class BookListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var bookList: List<Book> = arrayListOf()

    fun setBookList(bookList :List<Book>){
        this.bookList = bookList
        notifyDataSetChanged()
    }

    fun addBookList(bookList :List<Book>){
        this.bookList += bookList
        notifyItemRangeChanged(0, this.bookList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BookViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val book = bookList[position]

        return (holder as BookViewHolder).bind(book)
    }

}
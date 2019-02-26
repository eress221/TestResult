package com.eress.testresult.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*
import com.eress.testresult.R
import com.eress.testresult.adapter.BookListAdapter
import com.eress.testresult.model.Search
import com.eress.testresult.util.BackPressCloseUtil
import com.eress.testresult.viewmodel.BookListViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.eress.testresult.network.NetworkCheck
import com.google.android.material.snackbar.Snackbar

class ListActivity: BaseActivity() {
    private var backPressClose: BackPressCloseUtil? = null
    private var searchFlag = false
    private var currentQuery = ""
    private var totalPage = 0
    private var currentPage = 1
    private lateinit var viewModel: BookListViewModel
    private lateinit var adapter: BookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        backPressClose = BackPressCloseUtil(this)
        initialize()
    }

    private fun initialize() {
        viewModel = ViewModelProviders.of(this).get(BookListViewModel::class.java)
        viewModel.searchCallback.observe(this, Observer {
            if(it.total.toInt() > 0) {
                totalPage = Math.ceil((it.total.toInt()/10.0)).toInt()
                tv_no_data.visibility = View.INVISIBLE
            } else {
                tv_no_data.visibility = View.VISIBLE
                et_search.text = null
                currentQuery = ""
                totalPage = 0
                currentPage = 1
            }
            updateSearchBook(it)
        })
        viewModel.isLoading.observe(this, Observer { updateLoadingStatus(it) })

        adapter = BookListAdapter()

        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        );
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
        recycler_view.addOnScrollListener(recyclerViewOnScrollListener)

        ib_search.setOnClickListener {
            searchQuery()
        }

        Glide.with(this).load(R.raw.gif_loading).into(iv_loading);
        et_search.setOnEditorActionListener() { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                searchQuery()
                true
            } else {
                false
            }
        }

        if(!NetworkCheck(this).info()) {
            Snackbar.make(ib_search, getString(R.string.disconnect), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry)) {
                    searchQuery()
                }.show()
        }
    }

    private fun searchQuery() {
        if (!searchFlag) {
            tv_no_data.visibility = View.INVISIBLE
            val query = et_search.text.toString()
            currentQuery = query
            currentPage = 1
            viewModel.searchBookList(query, 1)
        }
    }

    private fun prevPage() {
        if (!searchFlag) {
            if (currentPage >= 0) {
                currentPage -= 1
                viewModel.searchBookList(currentQuery, currentPage)
            }
        }
    }

    private fun nextPage() {
        if (!searchFlag) {
            if (currentPage < totalPage) {
                currentPage += 1
                viewModel.searchBookList(currentQuery, currentPage)
            }
        }
    }

    private fun updateSearchBook(search: Search) {
        if (totalPage == 0) {
            adapter.setBookList(emptyList())
        } else if (currentPage == 1) {
            adapter.setBookList(search.books)
        } else {
            adapter.addBookList(search.books)
        }
    }

    private fun updateLoadingStatus(isLoading: Boolean) {
        if (isLoading) {
            iv_loading.visibility = View.VISIBLE
            searchFlag = true
        } else {
            iv_loading.visibility = View.INVISIBLE
            searchFlag = false
            et_search.hideKeyboard()
            if(currentPage == 1) {
                recycler_view.scrollToPosition(0)
            }
        }
    }

    fun View.hideKeyboard() {
        val service: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        service?.hideSoftInputFromWindow(windowToken, 0)
    }

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!recyclerView.canScrollVertically(1)) {
                nextPage()
            }
        }
    }

    override fun onBackPressed() {
        backPressClose!!.onBackPressed()
    }
}
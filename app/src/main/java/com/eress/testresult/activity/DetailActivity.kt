package com.eress.testresult.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import com.eress.testresult.R
import com.eress.testresult.network.NetworkCheck
import com.eress.testresult.viewmodel.BookDetailViewModel
import com.google.android.material.snackbar.Snackbar



class DetailActivity: BaseActivity() {

    private lateinit var viewModel: BookDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initialize()
    }

    private fun initialize() {
        viewModel = ViewModelProviders.of(this).get(BookDetailViewModel::class.java)
        viewModel.detailCallback.observe(this, Observer {
            Glide.with(this).load(it.image).into(iv_thumbnail)
            lb_title.text = getString(R.string.lb_title)
            tv_title.text = it.title
            lb_authors.text = getString(R.string.lb_authors)
            tv_authors.text = it.authors
            lb_price.text = getString(R.string.lb_price)
            tv_price.text = it.price
            lb_pages.text = getString(R.string.lb_pages)
            tv_pages.text = it.pages
            tv_desc.text = it.desc
        })
        viewModel.isLoading.observe(this, Observer { updateLoadingStatus(it) })
        Glide.with(this).load(R.raw.gif_loading).into(iv_loading)
        viewModel.detailOneBook(intent.getStringExtra("isbn13"))

        if(!NetworkCheck(this).info()) {
            Snackbar.make(iv_thumbnail, getString(R.string.disconnect), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry)) {
                    initialize()
                }.show()
        }
    }

    private fun updateLoadingStatus(isLoading: Boolean) {
        if (isLoading) {
            iv_loading.visibility = View.VISIBLE
        } else {
            iv_loading.visibility = View.INVISIBLE
        }
    }
}
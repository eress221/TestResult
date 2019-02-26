package com.eress.testresult.viewmodel

import android.app.Application
import com.eress.testresult.model.Book
import com.eress.testresult.network.ItBookStoreApi
import com.eress.testresult.util.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import sung.com.book.utils.SingleLiveEvent

class BookDetailViewModel(application: Application) : BaseViewModel(application) {

    var detailCallback = SingleLiveEvent<Book>()

    fun detailOneBook(isbn13: String) {
        isLoading.value = true

        compositeDisposable.add(
                ItBookStoreApi.create().detail(isbn13)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    detailCallback.value = it
                                    isLoading.value = false
                                    LogUtil.d("success = $it")
                                },
                                {
                                    isLoading.value = false
                                    LogUtil.d("error = $it")
                                }
                        )
        )
    }

}
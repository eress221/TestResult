package com.eress.testresult.viewmodel

import android.app.Application
import android.util.Log
import com.eress.testresult.model.Search
import com.eress.testresult.network.ItBookStoreApi
import com.eress.testresult.util.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import sung.com.book.utils.SingleLiveEvent

class BookListViewModel(application: Application) : BaseViewModel(application) {

    var searchCallback = SingleLiveEvent<Search>()

    fun searchBookList(query: String, page: Int) {
        isLoading.value = true

        compositeDisposable.add(
                ItBookStoreApi.create().search(query, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    searchCallback.value = it
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
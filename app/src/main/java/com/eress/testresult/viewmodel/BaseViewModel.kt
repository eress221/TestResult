package com.eress.testresult.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import sung.com.book.utils.SingleLiveEvent

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var isLoading = SingleLiveEvent<Boolean>()
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        isLoading.call()
        compositeDisposable.dispose()
        super.onCleared()
    }
}
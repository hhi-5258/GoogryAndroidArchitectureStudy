package com.tsdev.tsandroid.presenter

import com.tsdev.tsandroid.R
import com.tsdev.tsandroid.base.BasePresenter
import com.tsdev.tsandroid.data.Item
import com.tsdev.tsandroid.data.repository.NaverReopsitory
import com.tsdev.tsandroid.eventbus.RxEventBus
import com.tsdev.tsandroid.provider.ResourceProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviePresenter(
    override val view: MovieContract.View,
    private val movieRepositoryImpl: NaverReopsitory,
    private val resourceProvider: ResourceProvider,
    override val rxEventBus: RxEventBus
) : MovieContract.Presenter, BasePresenter<MovieContract.View>() {

    override var isLoading: Boolean = false

    override fun loadMovie(query: String) {
        isLoading = true
        compositeDisposable.add(
            movieRepositoryImpl.getMovieList(query)
                .subscribeOn(Schedulers.io())
                .onErrorReturn {
                    it.printStackTrace()
                    view.showToastMessage(resourceProvider.getResultErrorString(R.string.occur_error_toast))
                    emptyList()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.onHideSoftKeyboard()
                }
                .subscribe(view::showSearchResult)
        )
        isLoading = false
    }

    override fun onPressBackButton() {
        rxEventBus.sendBackButtonEvent(System.currentTimeMillis())
    }
}
package com.mamafarm.android.ranking.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JittaRankingViewModel : ViewModel() {

    private val _viewState = MutableLiveData<JittaRankingListViewState>()
    val viewState: LiveData<JittaRankingListViewState> get() = _viewState

    fun getStockRanking() {

    }
}
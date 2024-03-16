package com.mamafarm.android.ranking.presentation

import com.mamafarm.android.ranking.model.JittaRank

sealed class JittaRankingListViewState {
    data object Loading : JittaRankingListViewState()
    data class Content(val list: List<JittaRank>) : JittaRankingListViewState()
    data object Error : JittaRankingListViewState()
}
package com.mamafarm.android.ranking.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mamafarm.android.market.databinding.JittaItemRankBinding
import com.mamafarm.android.ranking.model.JittaRank

class JittaRankingPagingAdapter(
    private val onItemClicked: (JittaRank) -> Unit
) :
    PagingDataAdapter<JittaRank, JittaRankingPagingAdapter.JittaRankingViewHolder>(JittaRankDiffUtil()) {

    class JittaRankingViewHolder(
        private val binding: JittaItemRankBinding,
        private val onItemClicked: (JittaRank) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: JittaRank?) {
            item?.let { nonNullItem ->
                binding.root.setOnClickListener { onItemClicked.invoke(nonNullItem) }
                binding.tvRank.text = "${nonNullItem.rank}/${nonNullItem.total}"
                binding.tvCode.text = nonNullItem.id
                binding.tvTitle.text = nonNullItem.title
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onItemClicked: (JittaRank) -> Unit
            ): JittaRankingViewHolder {
                return JittaRankingViewHolder(
                    JittaItemRankBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClicked
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JittaRankingViewHolder {
        return JittaRankingViewHolder.from(parent, onItemClicked)
    }

    override fun onBindViewHolder(holder: JittaRankingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class JittaRankDiffUtil : DiffUtil.ItemCallback<JittaRank>() {
    override fun areItemsTheSame(oldItem: JittaRank, newItem: JittaRank): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: JittaRank, newItem: JittaRank): Boolean {
        return oldItem.rank == newItem.rank
                && oldItem.title == newItem.title
    }
}
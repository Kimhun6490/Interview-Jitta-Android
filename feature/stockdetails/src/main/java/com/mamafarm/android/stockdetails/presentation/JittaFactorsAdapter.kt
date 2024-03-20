package com.mamafarm.android.stockdetails.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mamafarm.android.stockdetails.databinding.JittaItemFactorBinding
import com.mamafarm.android.stockdetails.model.JittaFactor

class JittaFactorsAdapter(
    private val factors: List<JittaFactor>,
    private val onItemClicked: (JittaFactor) -> Unit
) :
    RecyclerView.Adapter<JittaFactorsAdapter.JittaFactorViewHolder>() {

    class JittaFactorViewHolder(
        private val binding: JittaItemFactorBinding,
        private val onItemClicked: (JittaFactor) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: JittaFactor?) {
            item?.let { nonNullItem ->
                binding.root.setOnClickListener { onItemClicked.invoke(nonNullItem) }
                binding.tvFactor.text = nonNullItem.factor
                binding.tvProgress.text = nonNullItem.value.toString()
                binding.progressBar.progress = nonNullItem.value
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onItemClicked: (JittaFactor) -> Unit
            ): JittaFactorViewHolder {
                return JittaFactorViewHolder(
                    JittaItemFactorBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClicked
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JittaFactorViewHolder {
        return JittaFactorViewHolder.from(parent, onItemClicked)
    }

    override fun getItemCount(): Int = factors.size

    override fun onBindViewHolder(holder: JittaFactorViewHolder, position: Int) {
        holder.bind(factors[position])
    }
}
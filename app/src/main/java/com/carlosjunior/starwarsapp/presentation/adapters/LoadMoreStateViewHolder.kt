package com.carlosjunior.starwarsapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.carlosjunior.starwarsapp.databinding.ItemLoadMoreStateBinding

class LoadMoreStateViewHolder(
    itemBinding: ItemLoadMoreStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val binding = ItemLoadMoreStateBinding.bind(itemView)
    private val progressBarLoadingMore = binding.progressLoadingMore
    private val textTryAgainMessage = binding.textTryAgain.also {
        it.setOnClickListener {
            retry()
        }
    }

    fun bind(loadSate: LoadState) {
        progressBarLoadingMore.isVisible = loadSate is LoadState.Loading
        textTryAgainMessage.isVisible = loadSate is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadMoreStateViewHolder {
            val itemBinding = ItemLoadMoreStateBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return LoadMoreStateViewHolder(itemBinding, retry)
        }
    }
}
package com.carlosjunior.starwarsapp.presentation.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class LoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadMoreStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadMoreStateViewHolder.create(parent, retry)

    override fun onBindViewHolder(
        holder: LoadMoreStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}
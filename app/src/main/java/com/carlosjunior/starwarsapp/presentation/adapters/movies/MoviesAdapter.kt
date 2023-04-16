package com.carlosjunior.starwarsapp.presentation.adapters.movies

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.carlosjunior.starwarsapp.presentation.model.MoviesViewObject

class MoviesAdapter(
    private val onItemClick: (MoviesViewObject, Int) -> Unit
): PagingDataAdapter<MoviesViewObject, MoviesViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {if (item != null) {onItemClick(item, position)}}
        getItem(position)?.let {holder.bind(it, position)}
    }

    companion object {
        private val diffCallback = object: DiffUtil.ItemCallback<MoviesViewObject>() {
            override fun areItemsTheSame(
                oldItem: MoviesViewObject,
                newItem: MoviesViewObject
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: MoviesViewObject,
                newItem: MoviesViewObject
            ): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}
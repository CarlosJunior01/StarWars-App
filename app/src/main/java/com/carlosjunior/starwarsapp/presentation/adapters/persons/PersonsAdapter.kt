package com.carlosjunior.starwarsapp.presentation.adapters.persons

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.carlosjunior.core.domain.model.Persons

class PersonsAdapter(
    private val onItemClick: (Persons, Int) -> Unit
): PagingDataAdapter<Persons, PersonsViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonsViewHolder {
        return PersonsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PersonsViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {if (item != null) {onItemClick(item, position)}}
        getItem(position)?.let {holder.bind(it, position)}
    }

    companion object {
        private val diffCallback = object: DiffUtil.ItemCallback<Persons>() {
            override fun areItemsTheSame(
                oldItem: Persons,
                newItem: Persons
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: Persons,
                newItem: Persons
            ): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}
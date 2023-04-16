package com.carlosjunior.starwarsapp.presentation.adapters.persons

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.carlosjunior.starwarsapp.presentation.model.PersonsViewObject

class PersonsAdapter(
    private val onItemClick: (PersonsViewObject, Int) -> Unit
): PagingDataAdapter<PersonsViewObject, PersonsViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonsViewHolder {
        return PersonsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PersonsViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {if (item != null) {onItemClick(item, position)}}
        getItem(position)?.let {holder.bind(it, position)}
    }

    companion object {
        private val diffCallback = object: DiffUtil.ItemCallback<PersonsViewObject>() {
            override fun areItemsTheSame(
                oldItem: PersonsViewObject,
                newItem: PersonsViewObject
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: PersonsViewObject,
                newItem: PersonsViewObject
            ): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}
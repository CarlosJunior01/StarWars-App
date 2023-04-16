package com.carlosjunior.starwarsapp.presentation.adapters.persons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carlosjunior.starwarsapp.R
import com.carlosjunior.starwarsapp.databinding.RecyclerPersonItemListBinding
import com.carlosjunior.starwarsapp.presentation.model.PersonsViewObject

class PersonsViewHolder(
    itemListBinding: RecyclerPersonItemListBinding
) : RecyclerView.ViewHolder(itemListBinding.root) {

    private val personName = itemListBinding.personName
    private val personYear = itemListBinding.personYear
    private val personGender = itemListBinding.personGender
    private val personImage = itemListBinding.personImage

    fun bind(persons: PersonsViewObject, position: Int) {
        val itemLoad = "$URL_IMAGE${position + NUMBER_ONE}$FORMAT"
        personName.text = persons.name
        personYear.text = persons.birthYear
        personGender.text = persons.gender

        Glide.with(itemView)
            .load(itemLoad)
            .fallback(R.drawable.ic_launcher_foreground)
            .into(personImage)
    }

    companion object {
        fun create(parent: ViewGroup): PersonsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = RecyclerPersonItemListBinding.inflate(inflater, parent, false)
            return PersonsViewHolder(itemBinding)
        }

        private const val NUMBER_ONE = 1
        private const val URL_IMAGE = "https://starwars-visualguide.com/assets/img/characters/"
        private const val FORMAT = ".jpg"
    }
}
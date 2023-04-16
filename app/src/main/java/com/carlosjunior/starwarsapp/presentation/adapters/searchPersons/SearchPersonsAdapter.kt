package com.carlosjunior.starwarsapp.presentation.adapters.searchPersons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carlosjunior.starwarsapp.R
import com.carlosjunior.starwarsapp.presentation.model.PersonsViewObject

class SearchPersonsAdapter(
    private val persons: List<PersonsViewObject>,
    private val onItemClickListener: ((events: PersonsViewObject, Int) -> Unit)
) : RecyclerView.Adapter<SearchPersonsAdapter.SearchPersonsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchPersonsAdapter.SearchPersonsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_person_item_list, parent, false)

        return SearchPersonsViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(
        holder: SearchPersonsAdapter.SearchPersonsViewHolder, position: Int
    ) {
        val persons = persons[position]

        holder.apply {
            val itemLoad = "${URL_IMAGE}${getItemListPosition(persons.url) + NUMBER_ONE}${FORMAT}"
            personName.text = persons.name
            personYear.text = persons.birthYear
            personGender.text = persons.gender

            itemView.setOnClickListener {
                onItemClickListener.invoke(persons, getItemListPosition(persons.url))
            }

            Glide.with(itemView)
                .load(itemLoad)
                .fallback(R.drawable.ic_launcher_foreground)
                .into(personImage)
        }
    }

    override fun getItemCount() = persons.size

    private fun getItemListPosition(url: String?) =
        url?.drop(TWENTY_NINE)?.replace(OLD_FORMAT, NEW_FORMAT).toString().toInt() - NUMBER_ONE

    inner class SearchPersonsViewHolder(
        itemView: View, private val onItemClickListener: ((events: PersonsViewObject, position: Int) -> Unit)
    ) : RecyclerView.ViewHolder(itemView) {
        val personName: TextView = itemView.findViewById(R.id.person_name)
        val personYear: TextView = itemView.findViewById(R.id.person_year)
        val personGender: TextView = itemView.findViewById(R.id.person_gender)
        val personImage: ImageView = itemView.findViewById(R.id.person_image)
    }

    companion object {
        private const val URL_IMAGE = "https://starwars-visualguide.com/assets/img/characters/"
        private const val FORMAT = ".jpg"
        private const val OLD_FORMAT = "/"
        private const val NEW_FORMAT = ""
        private const val NUMBER_ONE = 1
        private const val TWENTY_NINE = 29
    }
}

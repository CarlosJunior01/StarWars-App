package com.carlosjunior.starwarsapp.presentation.adapters.searchMovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carlosjunior.starwarsapp.R
import com.carlosjunior.starwarsapp.presentation.model.MoviesViewObject

class SearchMoviesAdapter(
    private val movies: List<MoviesViewObject>,
    private val onItemClickListener: ((movies: MoviesViewObject, Int) -> Unit)
) : RecyclerView.Adapter<SearchMoviesAdapter.SearchPersonsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchMoviesAdapter.SearchPersonsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_movie_item_list, parent, false)

        return SearchPersonsViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(
        holder: SearchMoviesAdapter.SearchPersonsViewHolder, position: Int
    ) {
        val movies = movies[position]

        holder.apply {
            val itemLoad = "${URL_IMAGE}${getItemListPosition(movies.url) + NUMBER_ONE}${FORMAT}"
            titleName.text = movies.title
            directorName.text = movies.director
            releaseDate.text = movies.releaseDate

            itemView.setOnClickListener {
                onItemClickListener.invoke(movies, getItemListPosition(movies.url))
            }

            Glide.with(itemView)
                .load(itemLoad)
                .fallback(R.drawable.ic_launcher_foreground)
                .into(movieImage)
        }
    }

    override fun getItemCount() = movies.size

    private fun getItemListPosition(url: String?) =
        url?.get(url.length - NUMBER_TWO).toString().toInt() - NUMBER_ONE

    inner class SearchPersonsViewHolder(
        itemView: View, private val onItemClickListener: ((movies: MoviesViewObject, position: Int) -> Unit)
    ) : RecyclerView.ViewHolder(itemView) {
        val titleName: TextView = itemView.findViewById(R.id.title_name)
        val directorName: TextView = itemView.findViewById(R.id.director_name)
        val releaseDate: TextView = itemView.findViewById(R.id.release_date)
        val movieImage: ImageView = itemView.findViewById(R.id.movie_image)
    }

    companion object {
        private const val URL_IMAGE = "https://starwars-visualguide.com/assets/img/films/"
        private const val FORMAT = ".jpg"
        private const val NUMBER_ONE = 1
        private const val NUMBER_TWO = 2
    }
}

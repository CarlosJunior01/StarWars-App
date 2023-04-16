package com.carlosjunior.starwarsapp.presentation.adapters.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carlosjunior.starwarsapp.R
import com.carlosjunior.starwarsapp.databinding.RecyclerMovieItemListBinding
import com.carlosjunior.starwarsapp.presentation.model.MoviesViewObject

class MoviesViewHolder(
    itemListBinding: RecyclerMovieItemListBinding
) : RecyclerView.ViewHolder(itemListBinding.root) {

    private val movieName = itemListBinding.titleName
    private val movieDate = itemListBinding.releaseDate
    private val movieDirector = itemListBinding.directorName
    private val movieImage = itemListBinding.movieImage

    fun bind(movies: MoviesViewObject, position: Int) {
        val itemLoad = "$URL_IMAGE${position + NUMBER_ONE}$FORMAT"
        movieName.text = movies.title
        movieDate.text = movies.releaseDate
        movieDirector.text = movies.director

        Glide.with(itemView)
            .load(itemLoad)
            .fallback(R.drawable.ic_launcher_foreground)
            .into(movieImage)
    }

    companion object {
        fun create(parent: ViewGroup): MoviesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = RecyclerMovieItemListBinding.inflate(inflater, parent, false)
            return MoviesViewHolder(itemBinding)
        }

        private const val NUMBER_ONE = 1
        private const val URL_IMAGE = "https://starwars-visualguide.com/assets/img/films/"
        private const val FORMAT = ".jpg"
    }
}
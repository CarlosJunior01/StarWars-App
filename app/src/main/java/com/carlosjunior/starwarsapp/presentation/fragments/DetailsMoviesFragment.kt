package com.carlosjunior.starwarsapp.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.carlosjunior.starwarsapp.R
import com.carlosjunior.starwarsapp.database.AppDatabase
import com.carlosjunior.starwarsapp.database.dao.FavoriteMovieDAO
import com.carlosjunior.starwarsapp.database.model.FavoriteMovie
import com.carlosjunior.starwarsapp.databinding.FragmentMoviesDetailsBinding
import com.carlosjunior.starwarsapp.presentation.model.MoviesViewObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsMoviesFragment : Fragment() {

    private val arguments: DetailsMoviesFragmentArgs by navArgs()
    private lateinit var appDatabase: AppDatabase
    private lateinit var favoriteMovieDAO: FavoriteMovieDAO
    private lateinit var binding: FragmentMoviesDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        .apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatabase()
        setupViews(view)
        clickListeners()
    }

    private fun setupViews(view: View) {
        val itemLoad = "${URL_IMAGE}${arguments.position + NUMBER_ONE}${FORMAT}"
        with(binding) {
            titleName.text = arguments.movies.title
            directorName.text = getString(R.string.director, arguments.movies.director)
            producerName.text = getString(R.string.producer, arguments.movies.producer)
            releaseDate.text =  getString(R.string.release_date, arguments.movies.releaseDate)
            movieDescription.text = arguments.movies.openingCrawl
        }

        Glide.with(view)
            .load(itemLoad)
            .fallback(R.drawable.ic_launcher_foreground)
            .into(binding.imgCover)
    }

    private fun clickListeners() {
        with(binding) {
            btnReturnButton.setOnClickListener { navigateHomeFragment() }
            btnShareButton.setOnClickListener { shareAction() }
            searchBarContainer.setOnClickListener { navigateSearchFragment() }
            imgAddFavorite.setOnClickListener {
                insertFavoritePerson(arguments.movies)
                txtFavorite.text = getString(R.string.favorite_successfully)
            }
        }
    }

    private fun initDatabase() {
        this.appDatabase = AppDatabase.getInstance(requireContext())
        this.favoriteMovieDAO = appDatabase.favoriteMovieDao()
    }

    private fun insertFavoritePerson(movieVO: MoviesViewObject) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteMovieDAO.insert(
                FavoriteMovie(
                    title = movieVO.title.toString(),
                    episodeId = movieVO.episodeId.toString().toInt(),
                    openingCrawl = movieVO.openingCrawl.toString(),
                    director = movieVO.director.toString(),
                    producer = movieVO.producer.toString(),
                    releaseDate = movieVO.releaseDate.toString(),
                    url = movieVO.url.toString()
                )
            )
        }
    }

    private fun shareAction() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = TEXT_TYPE
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            getString(R.string.share_movie, arguments.movies.title,
                arguments.movies.url, arguments.movies.openingCrawl
            )
        )
        startActivity(Intent.createChooser(shareIntent, getString(R.string.app_name)))
    }

    private fun navigateHomeFragment() = findNavController().navigate(R.id.action_detailsMoviesFragment_to_homeFragment)
    private fun navigateSearchFragment() = findNavController().navigate(R.id.action_detailsMoviesFragment_to_searchFragment)

    companion object {
        private const val NUMBER_ONE = 1
        private const val URL_IMAGE = "https://starwars-visualguide.com/assets/img/films/"
        private const val FORMAT = ".jpg"
        private const val TEXT_TYPE = "text/plain"
    }
}
package com.carlosjunior.starwarsapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.carlosjunior.starwarsapp.R
import com.carlosjunior.starwarsapp.database.AppDatabase
import com.carlosjunior.starwarsapp.database.dao.FavoriteMovieDAO
import com.carlosjunior.starwarsapp.database.dao.FavoritePersonDAO
import com.carlosjunior.starwarsapp.databinding.FragmentFavoriteBinding
import com.carlosjunior.starwarsapp.presentation.adapters.searchMovies.SearchMoviesAdapter
import com.carlosjunior.starwarsapp.presentation.adapters.searchPersons.SearchPersonsAdapter
import com.carlosjunior.starwarsapp.presentation.model.MoviesViewObject
import com.carlosjunior.starwarsapp.presentation.model.PersonsViewObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var appDatabase: AppDatabase
    private lateinit var favoritePersonDAO: FavoritePersonDAO
    private lateinit var favoriteMovieDAO: FavoriteMovieDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFavoriteBinding.inflate(inflater, container, false)
        .apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListeners()
        initDatabase()
        fetchDataBase()
    }

    private fun clickListeners() {
        binding.btnReturnButton.setOnClickListener { navigateHomeFragment() }
        binding.searchBarContainer.setOnClickListener { navigateSearchFragment() }
    }

    private fun initDatabase() {
        this.appDatabase = AppDatabase.getInstance(requireContext())
        this.favoritePersonDAO = appDatabase.favoritePersonDao()
        this.favoriteMovieDAO = appDatabase.favoriteMovieDao()
    }

    private fun fetchDataBase() {
        fetchPersonsDAO()
        fetchMoviesDAO()
    }

    private fun fetchPersonsDAO() {
        CoroutineScope(Dispatchers.IO).launch {
            val persons = favoritePersonDAO.searchAll()
            val personViewObject = persons.map { person -> PersonsViewObject(person) }
            for (person in persons) initPersonsFavoriteAdapter(personViewObject)

            if (persons.isNotEmpty()) {
                binding.recyclerViewPersons.visibility = View.VISIBLE
                binding.textFavoritePersons.visibility = View.VISIBLE
                binding.textFavoriteEmpty.visibility = View.GONE
            }
        }
    }

    private fun fetchMoviesDAO() {
        CoroutineScope(Dispatchers.IO).launch {
            val movies = favoriteMovieDAO.searchAll()
            val movieViewObject = movies.map { movie -> MoviesViewObject(movie) }
            for (movie in movies) initMoviesFavoriteAdapter(movieViewObject)

            if (movies.isNotEmpty()) {
                binding.recyclerViewMovies.visibility = View.VISIBLE
                binding.textFavoriteMovies.visibility = View.VISIBLE
                binding.textFavoriteEmpty.visibility = View.GONE
            }
        }
    }

    private fun initPersonsFavoriteAdapter(persons: List<PersonsViewObject>) {
        recyclerView = binding.recyclerViewPersons
        recyclerView.run {
            setHasFixedSize(true)
            adapter = SearchPersonsAdapter(persons) { person, position ->
                navigatePersonsDetailsFragment(person, position)
            }
        }
    }

    private fun initMoviesFavoriteAdapter(movies: List<MoviesViewObject>) {
        recyclerView = binding.recyclerViewMovies
        recyclerView.run {
            setHasFixedSize(true)
            adapter = SearchMoviesAdapter(movies) { movie, position ->
                navigateMoviesDetailsFragment(movie, position)
            }
        }
    }

    private fun navigateHomeFragment() = findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)

    private fun navigateSearchFragment() =
        findNavController().navigate(R.id.action_favoriteFragment_to_searchFragment)

    private fun navigatePersonsDetailsFragment(persons: PersonsViewObject, pos: Int) =
        findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToDetailsPersonsFragment(persons, pos))

    private fun navigateMoviesDetailsFragment(movies: MoviesViewObject, pos: Int) =
        findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmenttToDetailsMoviesFragment(movies, pos))
}
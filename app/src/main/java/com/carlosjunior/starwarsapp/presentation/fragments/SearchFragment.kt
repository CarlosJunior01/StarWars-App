package com.carlosjunior.starwarsapp.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carlosjunior.starwarsapp.R
import com.carlosjunior.starwarsapp.database.AppDatabase
import com.carlosjunior.starwarsapp.database.dao.MovieDAO
import com.carlosjunior.starwarsapp.database.dao.PersonDAO
import com.carlosjunior.starwarsapp.database.model.Movie
import com.carlosjunior.starwarsapp.database.model.Person
import com.carlosjunior.starwarsapp.databinding.FragmentSearchBinding
import com.carlosjunior.starwarsapp.presentation.adapters.searchMovies.SearchMoviesAdapter
import com.carlosjunior.starwarsapp.presentation.adapters.searchPersons.SearchPersonsAdapter
import com.carlosjunior.starwarsapp.presentation.model.MoviesViewObject
import com.carlosjunior.starwarsapp.presentation.model.PersonsViewObject
import com.carlosjunior.starwarsapp.presentation.viewmodels.HomeViewModel
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StatePersonsError
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StatePersonsLoading
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StateSearchMoviesSuccess
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StateSearchPersonsSuccess
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var appDatabase: AppDatabase
    private lateinit var personDAO: PersonDAO
    private lateinit var movieDAO: MovieDAO
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater, container, false)
        .apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatabase()
        clickListeners()
    }

    private fun initPersonsAdapter(persons: List<PersonsViewObject>) {
        recyclerView = binding.recyclerViewSearchPersons
        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter = SearchPersonsAdapter(persons) { persons, position ->
                navigatePersonsDetailsFragment(persons, position)
                insertPerson(persons)
            }
        }
    }

    private fun initMoviesAdapter(movies: List<MoviesViewObject>) {
        recyclerView = binding.recyclerViewSearchMovies
        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter = SearchMoviesAdapter(movies) { movies, position ->
                navigateMoviesDetailsFragment(movies, position)
                insertMovie(movies)
            }
        }
    }

    private fun collectPersonsState() {
        lifecycleScope.launchWhenCreated {
            viewModel.screenState.collect { state ->
                when (state) {
                    is StatePersonsLoading -> {
                        binding.recyclerViewSearchMovies.visibility = View.GONE
                    }
                    is StateSearchPersonsSuccess -> {
                        binding.recyclerViewSearchPersons.visibility = View.VISIBLE
                        initPersonsAdapter(state.personsVO)
                    }
                    is StatePersonsError -> binding.recyclerViewSearchMovies.visibility = View.GONE
                    else ->  binding.recyclerViewSearchPersons.visibility = View.GONE
                }
            }
        }
    }

    private fun collectMovieState() {
        lifecycleScope.launchWhenCreated {
            viewModel.screenState.collect { state ->
                when (state) {
                    is StatePersonsLoading -> {
                        binding.recyclerViewSearchPersons.visibility = View.GONE
                    }
                    is StateSearchMoviesSuccess -> {
                        binding.recyclerViewSearchMovies.visibility = View.VISIBLE
                        initMoviesAdapter(state.moviesVO)
                    }
                    is StatePersonsError -> binding.recyclerViewSearchMovies.visibility = View.GONE
                    else -> binding.recyclerViewSearchMovies.visibility = View.GONE
                }
            }
        }
    }

    private fun initDatabase() {
        this.appDatabase = AppDatabase.getInstance(requireContext())
        this.personDAO = appDatabase.personDao()
        this.movieDAO = appDatabase.movieDao()
    }

    private fun insertPerson(personVO: PersonsViewObject) {
        CoroutineScope(Dispatchers.IO).launch {
            personDAO.insert(
                Person(
                    name = personVO.name.toString(),
                    height = personVO.height.toString(),
                    hairColor = personVO.hairColor.toString(),
                    skinColor = personVO.skinColor.toString(),
                    birthYear = personVO.birthYear.toString(),
                    gender = personVO.gender.toString(),
                    url = personVO.url.toString()
                )
            )
        }
    }

    private fun insertMovie(movieVO: MoviesViewObject) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDAO.insert(
                Movie(
                    title = movieVO.title.toString(),
                    episodeId = movieVO.episodeId.toString().toInt(),
                    openingCrawl = movieVO.toString(),
                    director = movieVO.director.toString(),
                    producer = movieVO.producer.toString(),
                    releaseDate = movieVO.releaseDate.toString(),
                    url = movieVO.url.toString()
                )
            )
        }
    }

    private fun clickListeners() {
        var searchDefaultType = true

        with(binding) {
            btnReturnButton.setOnClickListener { navigateHomeFragment() }

            buttonPersons.setOnClickListener {
                searchDefaultType = true
                binding.editTextSearchBar.hint = getString(R.string.search_hint_persons_description)
                binding.imgPersonFilter.visibility = View.VISIBLE
                binding.imgMovieFilter.visibility = View.INVISIBLE
            }

            buttonMovies.setOnClickListener {
                searchDefaultType = false
                binding.editTextSearchBar.hint = getString(R.string.search_hint_movies_description)
                binding.imgPersonFilter.visibility = View.INVISIBLE
                binding.imgMovieFilter.visibility = View.VISIBLE
            }

            editTextSearchBar.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(editable: Editable?) {
                    val editText = editable.toString()

                    if (editText.length >= TEXT_LENGTH) {
                        when (searchDefaultType) {
                            true -> {
                                viewModel.personsSearch(editText)
                                collectPersonsState()
                            }
                            false -> {
                                viewModel.moviesSearch(editText)
                                collectMovieState()
                            }
                        }
                    }
                }
            })
        }
    }

    private fun navigateHomeFragment() = findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
    private fun navigatePersonsDetailsFragment(persons: PersonsViewObject, pos: Int) =
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsPersonsFragment(persons, pos))
    private fun navigateMoviesDetailsFragment(movies: MoviesViewObject, pos: Int) =
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsMoviesFragment(movies, pos))

    companion object {
        private const val SPAN_COUNT = 2
        private const val TEXT_LENGTH = 2
    }
}
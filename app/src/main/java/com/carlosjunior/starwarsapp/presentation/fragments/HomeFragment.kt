package com.carlosjunior.starwarsapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.carlosjunior.starwarsapp.R
import com.carlosjunior.starwarsapp.database.AppDatabase
import com.carlosjunior.starwarsapp.database.dao.MovieDAO
import com.carlosjunior.starwarsapp.database.dao.PersonDAO
import com.carlosjunior.starwarsapp.database.model.Movie
import com.carlosjunior.starwarsapp.database.model.Person
import com.carlosjunior.starwarsapp.databinding.FragmentHomeBinding
import com.carlosjunior.starwarsapp.presentation.adapters.LoadStateAdapter
import com.carlosjunior.starwarsapp.presentation.adapters.movies.MoviesAdapter
import com.carlosjunior.starwarsapp.presentation.adapters.persons.PersonsAdapter
import com.carlosjunior.starwarsapp.presentation.adapters.searchMovies.SearchMoviesAdapter
import com.carlosjunior.starwarsapp.presentation.adapters.searchPersons.SearchPersonsAdapter
import com.carlosjunior.starwarsapp.presentation.model.MoviesViewObject
import com.carlosjunior.starwarsapp.presentation.model.PersonsViewObject
import com.carlosjunior.starwarsapp.presentation.viewmodels.HomeViewModel
import com.carlosjunior.starwarsapp.presentation.viewmodels.StateMovieResponse.StateMoviesSuccess
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StatePersonsError
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StatePersonsSuccess
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var personsAdapter: PersonsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var appDatabase: AppDatabase
    private lateinit var personDAO: PersonDAO
    private lateinit var movieDAO: MovieDAO
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false)
        .apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatabase()
        initPersonsAdapter()
        initMoviesAdapter()
        observeStateLoad()
        collectPersonsStateLoad()
        collectMoviesStateLoad()
        clickListeners()
        fetchDataBase()

    }

    private fun observeStateLoad() {
        lifecycleScope.launchWhenCreated {
            personsAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> switchFlipperChild(SHOW_CHILD_ZERO)
                    is LoadState.NotLoading -> switchFlipperChild(SHOW_CHILD_ONE)
                    is LoadState.Error -> switchFlipperChild(SHOW_CHILD_TWO)
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            moviesAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> binding.homeScreen.recyclerViewMovies.visibility =
                        View.GONE
                    is LoadState.NotLoading -> binding.homeScreen.recyclerViewMovies.visibility =
                        View.VISIBLE
                    is LoadState.Error -> binding.homeScreen.recyclerViewMovies.visibility =
                        View.GONE
                }
            }
        }
    }

    private fun collectPersonsStateLoad() {
        lifecycleScope.launchWhenCreated {
            viewModel.screenState.collect { state ->
                when (state) {
                    is StatePersonsSuccess -> personsAdapter.submitData(state.it)
                    is StatePersonsError -> Toast.makeText(requireContext(), ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
                    else -> {}
                }
            }
        }
    }

    private fun collectMoviesStateLoad() {
        lifecycleScope.launchWhenCreated {
            viewModel.screenMovieState.collect { state ->
                when (state) {
                    is StateMoviesSuccess -> moviesAdapter.submitData(state.it)
                    else -> {}
                }
            }
        }
    }

    private fun initPersonsAdapter() {
        personsAdapter = PersonsAdapter { it, pos ->
            navigatePersonsDetailsFragment(it, pos)
            insertPerson(it)
        }
        binding.homeScreen.recyclerViewPersons.run {
            setHasFixedSize(true)
            adapter = personsAdapter.withLoadStateFooter(
                footer = LoadStateAdapter(
                    personsAdapter::retry
                )
            )
        }
    }

    private fun initMoviesAdapter() {
        moviesAdapter = MoviesAdapter { it, pos ->
            navigateMoviesDetailsFragment(it, pos)
            insertMovie(it)
        }
        binding.homeScreen.recyclerViewMovies.run {
            setHasFixedSize(true)
            adapter = moviesAdapter.withLoadStateFooter(footer = LoadStateAdapter(moviesAdapter::retry))
        }
    }

    private fun initPersonsLastViewedAdapter(persons: List<PersonsViewObject>) {
        recyclerView = binding.homeScreen.recyclerViewLastPersonsViewed
        recyclerView.run {
            setHasFixedSize(true)
            adapter = SearchPersonsAdapter(persons) { person, position ->
                navigatePersonsDetailsFragment(person, position)
            }
        }
    }

    private fun initMoviesLastViewedAdapter(movies: List<MoviesViewObject>) {
        recyclerView = binding.homeScreen.recyclerViewLastMoviesViewed
        recyclerView.run {
            setHasFixedSize(true)
            adapter = SearchMoviesAdapter(movies) { movie, position ->
                navigateMoviesDetailsFragment(movie, position)
            }
        }
    }

    private fun switchFlipperChild(childState: Int) {
        when (childState) {
            SHOW_CHILD_ZERO -> binding.viewFlipperFragment.displayedChild = SHOW_CHILD_ZERO
            SHOW_CHILD_ONE -> binding.viewFlipperFragment.displayedChild = SHOW_CHILD_ONE
            SHOW_CHILD_TWO -> binding.viewFlipperFragment.displayedChild = SHOW_CHILD_TWO
        }
    }

    private fun clickListeners() {
        binding.homeScreen.searchBarContainer.setOnClickListener { navigateSearchFragment() }
        binding.homeScreen.imgFavorite.setOnClickListener { navigateFavoriteFragment() }
        binding.errorScreen.btnTryAgain.setOnClickListener { onRetry() }
    }

    private fun onRetry() {
        viewModel.personsPagingData(query = HomeViewModel.EMPTY)
        viewModel.moviesPagingData(query = HomeViewModel.EMPTY)
        observeStateLoad()
        collectPersonsStateLoad()
        collectMoviesStateLoad()
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

    private fun fetchDataBase() {
        fetchPersonsDAO()
        fetchMoviesDAO()
    }

    private fun fetchPersonsDAO() {
        CoroutineScope(Dispatchers.IO).launch {
            val persons = personDAO.searchAll()
            val personViewObject = persons.map { person -> PersonsViewObject(person) }
            for (person in persons) initPersonsLastViewedAdapter(personViewObject)

            if (persons.isNotEmpty()) {
                binding.homeScreen.recyclerViewLastPersonsViewed.visibility = View.VISIBLE
                binding.homeScreen.textLastPersonsViewed.visibility = View.VISIBLE
            }
        }
    }

    private fun fetchMoviesDAO() {
        CoroutineScope(Dispatchers.IO).launch {
            val movies = movieDAO.searchAll()
            val movieViewObject = movies.map { movie -> MoviesViewObject(movie) }
            for (movie in movies) initMoviesLastViewedAdapter(movieViewObject)

            if (movies.isNotEmpty()) {
                binding.homeScreen.recyclerViewLastMoviesViewed.visibility = View.VISIBLE
                binding.homeScreen.textLastMoviesViewed.visibility = View.VISIBLE
            }
        }
    }

    private fun navigateSearchFragment() =
        findNavController().navigate(R.id.action_homeFragment_to_searchFragment)

    private fun navigateFavoriteFragment() =
        findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)

    private fun navigatePersonsDetailsFragment(persons: PersonsViewObject, pos: Int) =
        findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToDetailsPersonsFragment(persons, pos))

    private fun navigateMoviesDetailsFragment(movies: MoviesViewObject, pos: Int) =
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailsMoviesFragment(movies, pos))

    companion object {
        private const val SHOW_CHILD_ZERO = 0
        private const val SHOW_CHILD_ONE = 1
        private const val SHOW_CHILD_TWO = 2
        private const val ERROR_MESSAGE = ""
    }
}
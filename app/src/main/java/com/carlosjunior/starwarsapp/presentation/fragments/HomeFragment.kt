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
import com.carlosjunior.starwarsapp.R
import com.carlosjunior.starwarsapp.databinding.FragmentHomeBinding
import com.carlosjunior.starwarsapp.presentation.adapters.LoadStateAdapter
import com.carlosjunior.starwarsapp.presentation.adapters.movies.MoviesAdapter
import com.carlosjunior.starwarsapp.presentation.adapters.persons.PersonsAdapter
import com.carlosjunior.starwarsapp.presentation.model.MoviesViewObject
import com.carlosjunior.starwarsapp.presentation.model.PersonsViewObject
import com.carlosjunior.starwarsapp.presentation.viewmodels.HomeViewModel
import com.carlosjunior.starwarsapp.presentation.viewmodels.StateMovieResponse.StateMoviesSuccess
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse.StatePersonsSuccess
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var personsAdapter: PersonsAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false)
        .apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPersonsAdapter()
        initMoviesAdapter()
        observeStateLoad()
        collectPersonsStateLoad()
        collectMoviesStateLoad()
        clickListeners()

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
                    is LoadState.Loading -> binding.homeScreen.recyclerViewMovies.visibility = View.GONE
                    is LoadState.NotLoading -> binding.homeScreen.recyclerViewMovies.visibility = View.VISIBLE
                    is LoadState.Error -> binding.homeScreen.recyclerViewMovies.visibility = View.GONE
                }
            }
        }
    }

    private fun collectPersonsStateLoad() {
        lifecycleScope.launchWhenCreated {
            viewModel.screenState.collect { state ->
                when (state) {
                    is StatePersonsSuccess -> personsAdapter.submitData(state.it)
                    else -> Toast.makeText(requireContext(), ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun collectMoviesStateLoad() {
        lifecycleScope.launchWhenCreated {
            viewModel.screenMovieState.collect { state ->
                when (state) {
                    is StateMoviesSuccess -> {
                        moviesAdapter.submitData(state.it)
                    }
                    else -> Toast.makeText(requireContext(), ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initPersonsAdapter() {
        personsAdapter = PersonsAdapter { it, pos ->
            navigatePersonsDetailsFragment(it, pos)
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
        moviesAdapter = MoviesAdapter {it, pos ->
            navigateMoviesDetailsFragment(it, pos)
        }
        binding.homeScreen.recyclerViewMovies.run {
            setHasFixedSize(true)
            adapter = moviesAdapter.withLoadStateFooter(
                footer = LoadStateAdapter(
                    moviesAdapter::retry
                )
            )
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
        binding.homeScreen.searchBarContainer.setOnClickListener {navigateSearchFragment()}
    }

    private fun navigateSearchFragment() = findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
    private fun navigatePersonsDetailsFragment(persons: PersonsViewObject, pos: Int) =
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsPersonsFragment(persons, pos))
    private fun navigateMoviesDetailsFragment(movies: MoviesViewObject, pos: Int) =
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsMoviesFragment(movies, pos))

    companion object {
        private const val SHOW_CHILD_ZERO = 0
        private const val SHOW_CHILD_ONE = 1
        private const val SHOW_CHILD_TWO = 2
        private const val ERROR_MESSAGE = ""
    }
}
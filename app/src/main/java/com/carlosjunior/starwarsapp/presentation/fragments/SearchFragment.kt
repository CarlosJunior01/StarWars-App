package com.carlosjunior.starwarsapp.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carlosjunior.starwarsapp.R
import com.carlosjunior.starwarsapp.databinding.FragmentSearchBinding
import com.carlosjunior.starwarsapp.presentation.adapters.searchMovies.SearchMoviesAdapter
import com.carlosjunior.starwarsapp.presentation.adapters.searchPersons.SearchPersonsAdapter
import com.carlosjunior.starwarsapp.presentation.model.MoviesViewObject
import com.carlosjunior.starwarsapp.presentation.model.PersonsViewObject
import com.carlosjunior.starwarsapp.presentation.viewmodels.HomeViewModel
import com.carlosjunior.starwarsapp.presentation.viewmodels.StatePersonsResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var recyclerView: RecyclerView
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater, container, false)
        .apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListeners()
    }

    private fun initPersonsAdapter(persons: List<PersonsViewObject>) {
        recyclerView = binding.recyclerViewSearchPersons
        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter = SearchPersonsAdapter(persons) { persons, position ->
                navigatePersonsDetailsFragment(persons, position)
                Toast.makeText(context, persons.name, Toast.LENGTH_SHORT).show()
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
            }
        }
    }

    private fun collectPersonsState() {
        lifecycleScope.launchWhenCreated {
            viewModel.screenState.collect { state ->
                when (state) {
                    is StatePersonsResponse.StatePersonsLoading -> {
                        binding.recyclerViewSearchMovies.visibility = View.GONE
                    }
                    is StatePersonsResponse.StateSearchPersonsSuccess -> {
                        binding.recyclerViewSearchPersons.visibility = View.VISIBLE
                        initPersonsAdapter(state.personsVO)
                    }
                    else -> {
                        binding.recyclerViewSearchPersons.visibility = View.GONE
                        Toast.makeText(requireContext(), ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun collectMovieState() {
        lifecycleScope.launchWhenCreated {
            viewModel.screenState.collect { state ->
                when (state) {
                    is StatePersonsResponse.StatePersonsLoading -> {
                        binding.recyclerViewSearchPersons.visibility = View.GONE
                    }
                    is StatePersonsResponse.StateSearchMoviesSuccess -> {
                        binding.recyclerViewSearchMovies.visibility = View.VISIBLE
                        initMoviesAdapter(state.moviesVO)
                    }
                    else -> {
                        binding.recyclerViewSearchMovies.visibility = View.GONE
                        Toast.makeText(requireContext(), ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun clickListeners() {
        var searchDefaultType = true

        with(binding) {
            btnReturnButton.setOnClickListener { findNavController().popBackStack() }

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

    private fun navigatePersonsDetailsFragment(persons: PersonsViewObject, pos: Int) =
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsPersonsFragment(persons, pos))
    private fun navigateMoviesDetailsFragment(movies: MoviesViewObject, pos: Int) =
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsMoviesFragment(movies, pos))

    companion object {
        private const val SPAN_COUNT = 2
        private const val TEXT_LENGTH = 3
        private const val ERROR_MESSAGE = ""
    }
}
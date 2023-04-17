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
import com.carlosjunior.starwarsapp.database.dao.FavoritePersonDAO
import com.carlosjunior.starwarsapp.database.model.FavoritePerson
import com.carlosjunior.starwarsapp.databinding.FragmentPersonsDetailsBinding
import com.carlosjunior.starwarsapp.presentation.model.PersonsViewObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsPersonsFragment : Fragment() {

    private val arguments: DetailsPersonsFragmentArgs by navArgs()
    private lateinit var appDatabase: AppDatabase
    private lateinit var favoritePersonDAO: FavoritePersonDAO
    private lateinit var binding: FragmentPersonsDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPersonsDetailsBinding.inflate(inflater, container, false)
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
            personName.text = arguments.persons.name
            genderName.text = getString(R.string.gender, arguments.persons.gender)
            heightValue.text = getString(R.string.height, arguments.persons.height)
            hairColor.text = getString(R.string.hair_color, arguments.persons.hairColor)
            yearDate.text = getString(R.string.birth_year, arguments.persons.birthYear)
        }

        Glide.with(view)
            .load(itemLoad)
            .fallback(R.drawable.ic_launcher_foreground)
            .into(binding.imgCover)
    }

    private fun clickListeners() {
        with(binding) { btnReturnButton.setOnClickListener { navigateHomeFragment() }
            btnShareButton.setOnClickListener { shareAction() }
            searchBarContainer.setOnClickListener { navigateSearchFragment() }
            imgAddFavorite.setOnClickListener {
                insertFavoritePerson(arguments.persons)
                txtFavorite.text = getString(R.string.favorite_successfully)
            }
        }
    }

    private fun initDatabase() {
        this.appDatabase = AppDatabase.getInstance(requireContext())
        this.favoritePersonDAO = appDatabase.favoritePersonDao()
    }

    private fun insertFavoritePerson(personVO: PersonsViewObject) {
        CoroutineScope(Dispatchers.IO).launch {
            favoritePersonDAO.insert(
                FavoritePerson(
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

    private fun shareAction() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = TEXT_TYPE
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_person, arguments.persons.name, arguments.persons.url))
        startActivity(Intent.createChooser(shareIntent, getString(R.string.app_name)))
    }

    private fun navigateHomeFragment() = findNavController().navigate(R.id.action_detailsPersonsFragment_to_homeFragment)
    private fun navigateSearchFragment() = findNavController().navigate(R.id.action_detailsPersonsFragment_to_searchFragment)

    companion object {
        private const val NUMBER_ONE = 1
        private const val URL_IMAGE = "https://starwars-visualguide.com/assets/img/characters/"
        private const val FORMAT = ".jpg"
        private const val TEXT_TYPE= "text/plain"
    }
}
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
import com.carlosjunior.starwarsapp.databinding.FragmentPersonsDetailsBinding

class DetailsPersonsFragment : Fragment() {

    private val arguments: DetailsPersonsFragmentArgs by navArgs()
    private lateinit var binding: FragmentPersonsDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPersonsDetailsBinding.inflate(inflater, container, false)
        .apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        with(binding) {
            btnReturnButton.setOnClickListener { findNavController().popBackStack() }
            btnShareButton.setOnClickListener { shareAction() }
        }
    }

    private fun shareAction() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = TEXT_TYPE
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_person, arguments.persons.name, arguments.persons.url))
        startActivity(Intent.createChooser(shareIntent, getString(R.string.app_name)))
    }

    companion object {
        private const val NUMBER_ONE = 1
        private const val URL_IMAGE = "https://starwars-visualguide.com/assets/img/characters/"
        private const val FORMAT = ".jpg"
        private const val TEXT_TYPE= "text/plain"
    }
}
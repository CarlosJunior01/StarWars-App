package com.carlosjunior.starwarsapp.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.carlosjunior.starwarsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}
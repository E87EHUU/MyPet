package com.example.mypet.ui.food.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentFoodDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodDetailFragment : Fragment(R.layout.fragment_food_detail) {
    private val binding by viewBinding(FragmentFoodDetailBinding::bind)
    private val viewModel by viewModels<FoodDetailViewModel>()
    private val args by navArgs<FoodDetailFragmentArgs>()

}
package com.example.mypet.ui.care.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CareDetailFragment : Fragment(R.layout.fragment_care_detail) {
    private val binding by viewBinding(FragmentCareDetailBinding::bind)
    private val viewModel by viewModels<CareDetailViewModel>()
    private val args by navArgs<CareDetailFragmentArgs>()
}
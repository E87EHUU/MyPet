package com.example.mypet.ui.care

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CareFragment : Fragment(R.layout.fragment_care) {
    private val binding by viewBinding(FragmentCareBinding::bind)
    private val viewModel by viewModels<CareViewModel>()
    private val args by navArgs<CareFragmentArgs>()

}
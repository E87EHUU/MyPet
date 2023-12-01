package com.example.mypet.ui.care

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareBinding
import com.example.mypet.domain.care.CareViewHolderAlarmModel
import com.example.mypet.domain.care.CareViewHolderModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CareFragment : Fragment(R.layout.fragment_care) {
    private val binding by viewBinding(FragmentCareBinding::bind)
    private val viewModel by viewModels<CareViewModel>()
    private val args by navArgs<CareFragmentArgs>()

    private val careFoodCallback =
        object : CareFoodCallback {
            override fun onItemClick() {
                TODO("Not yet implemented")
            }
        }

    private val careRepeatCallback =
        object : CareRepeatCallback {
            override fun onItemClick() {
                navToRepeat()
            }
        }

    private val careAlarmCallback =
        object : CareAlarmCallback {
            override fun onItemClick(careAlarmModel: CareViewHolderAlarmModel) {
                navToAlarmDetail(careAlarmModel)
            }

            override fun onSwitchActive(careAlarmModel: CareViewHolderAlarmModel) {
                viewModel.switchAlarmState(careAlarmModel)
            }

        }

    private val adapter = CareAdapter(careFoodCallback, careRepeatCallback, careAlarmCallback)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserveCareViewHolderModels()
        binding.recyclerViewCare.adapter = adapter

        viewModel.updateCare(args.careId, args.careTypeOrdinal)
    }

    private fun initObserveCareViewHolderModels() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.careViewHolderModels.collectLatest {
                    if (it.isEmpty()) onEmptyCareModels()
                    else onUpdateCareModels(it)
                }
            }
        }
    }

    private fun onUpdateCareModels(careModels: List<CareViewHolderModel>) {
        adapter.submitList(careModels)
    }

    private fun onEmptyCareModels() {

    }

    private fun navToRepeat() {
        val directions = CareFragmentDirections.actionCareFragmentToRepeatFragment()
        findNavController().navigate(directions)
    }

    private fun navToAlarmDetail(careViewHolderAlarmModel: CareViewHolderAlarmModel) {
        val directions = CareFragmentDirections
            .actionCareFragmentToAlarmDetailFragment(careViewHolderAlarmModel.id)
        findNavController().navigate(directions)
    }
}
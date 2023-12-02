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
import com.example.mypet.domain.alarm.AlarmMinModel
import com.example.mypet.ui.care.alarm.CareAlarmCallback
import com.example.mypet.ui.care.main.CareMainCallback
import com.example.mypet.ui.care.repeat.CareRepeatCallback
import com.example.mypet.ui.care.start.CareStartCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CareFragment : Fragment(R.layout.fragment_care),
    CareMainCallback, CareStartCallback, CareRepeatCallback, CareAlarmCallback {
    private val binding by viewBinding(FragmentCareBinding::bind)
    private val viewModel by viewModels<CareViewModel>()
    private val args by navArgs<CareFragmentArgs>()

    private val adapter = CareAdapter(this, this, this, this)

    override fun onStart() {
        super.onStart()
        viewModel.updateCare(args.careId, args.careTypeOrdinal)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserveCareMainModel()
        initObserveCareStartModel()
        initObserveCareRepeatModel()
        initObserveCareAlarmModel()
    }

    private fun initView() {
        binding.recyclerViewCare.adapter = adapter
    }

    private fun initObserveCareMainModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.careMainModel.collectLatest {
                    adapter.careMainModel = it
                    binding.root.post {
                        adapter.notifyItemChanged(CareAdapter.MAIN_POSITION)
                    }
                }
            }
        }
    }

    private fun initObserveCareStartModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.careStartModel.collectLatest {
                    adapter.careStartModel = it
                    binding.root.post {
                        adapter.notifyItemChanged(CareAdapter.START_POSITION)
                    }
                }
            }
        }
    }

    private fun initObserveCareRepeatModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.careRepeatModel.collectLatest {
                    adapter.careRepeatModel = it
                    binding.root.post {
                        adapter.notifyItemChanged(CareAdapter.REPEAT_POSITION)
                    }
                }
            }
        }
    }

    private fun initObserveCareAlarmModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.careAlarmModel.collectLatest {
                    adapter.careAlarmModel = it
                    binding.root.post {
                        adapter.notifyItemChanged(CareAdapter.ALARM_POSITION)
                    }
                }
            }
        }
    }

    private fun navToRepeat() {
        val directions = CareFragmentDirections.actionCareFragmentToRepeatFragment()
        findNavController().navigate(directions)
    }

    private fun navToAlarmDetail(alarmMinModel: AlarmMinModel) {
        val directions = CareFragmentDirections
            .actionCareFragmentToAlarmDetailFragment(alarmMinModel.id)
        findNavController().navigate(directions)
    }

    override fun onClickAlarm(alarmMinModel: AlarmMinModel) {
        navToAlarmDetail(alarmMinModel)
    }

    override fun onSwitchAlarmStart(alarmMinModel: AlarmMinModel) {
        viewModel.switchAlarmState(alarmMinModel)
    }

    override fun onClickRepeat() {
        navToRepeat()
    }
}
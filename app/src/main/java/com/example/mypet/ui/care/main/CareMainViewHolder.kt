package com.example.mypet.ui.care.main

import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerMainBinding
import com.example.mypet.domain.care.CareMainModel

class CareMainViewHolder(
    private val binding: FragmentCareRecyclerMainBinding,
    private val callback: CareMainCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    private lateinit var careMainModel: CareMainModel

    init {
        with(binding) {
            textInputEditTextCareRecyclerMainTitle.doOnTextChanged { text, _, _, _ ->
                careMainModel.title = text.toString()
            }
            textInputEditTextCareRecyclerMainDose.doOnTextChanged { text, _, _, _ ->
                careMainModel.dose = text.toString()
            }
        }
    }

    fun bind(careMainModel: CareMainModel?) {
        careMainModel?.let {
            this.careMainModel = careMainModel

            filterUI()

            binding.textInputEditTextCareRecyclerMainTitle.setText(careMainModel.title)
            binding.textInputEditTextCareRecyclerMainDose.setText(careMainModel.dose)

            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
    }

    private fun filterUI() {
/*        when (careMainModel.careType) {
            FOOD -> {
                with(binding) {
                    textInputLayoutsCareRecyclerMainNote.isVisible = true
                }
            }

            BATH,
            TOOTHBRUSHING,
            COMBING_THE_WOOL,
            WALK -> {

            }

            MEDICINE,
            VITAMIN -> {
                with(binding) {
                    textInputLayoutsCareRecyclerMainTitle.isVisible = true
                    textInputLayoutsCareRecyclerMainNote.isVisible = true
                    textInputLayoutsCareRecyclerMainDose.isVisible = true
                }
            }

            VACCINATION,
            GROOMING,
            TRAINING,
            CHECKUP,
            AGAINST_FLEAS_WORMS,
            AGAINST_FLEAS_AND_TICKS -> {
                with(binding) {
                    textInputLayoutsCareRecyclerMainTitle.isVisible = true
                    textInputLayoutsCareRecyclerMainNote.isVisible = true
                }
            }
        }*/
    }
}
package com.example.mypet.ui.care.main

import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerMainBinding
import com.example.mypet.domain.care.CareMainModel
import com.example.mypet.domain.care.CareTypes.AGAINST_FLEAS_AND_TICKS
import com.example.mypet.domain.care.CareTypes.AGAINST_FLEAS_WORMS
import com.example.mypet.domain.care.CareTypes.BATH
import com.example.mypet.domain.care.CareTypes.CHECKUP
import com.example.mypet.domain.care.CareTypes.COMBING_THE_WOOL
import com.example.mypet.domain.care.CareTypes.FOOD
import com.example.mypet.domain.care.CareTypes.GROOMING
import com.example.mypet.domain.care.CareTypes.MEDICINE
import com.example.mypet.domain.care.CareTypes.TOOTHBRUSHING
import com.example.mypet.domain.care.CareTypes.TRAINING
import com.example.mypet.domain.care.CareTypes.VACCINATION
import com.example.mypet.domain.care.CareTypes.VITAMIN
import com.example.mypet.domain.care.CareTypes.WALK

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
            textInputEditTextCareRecyclerMainNote.doOnTextChanged { text, _, _, _ ->
                careMainModel.note = text.toString()
            }
            textInputEditTextCareRecyclerMainDose.doOnTextChanged { text, _, _, _ ->
                careMainModel.dose = text.toString()
            }
        }
    }

    fun bind(careMainModel: CareMainModel?) {
        careMainModel?.let {
            this.careMainModel = careMainModel

            /*            binding.imageViewCareRecyclerMainIcon.setImageResource(careMainModel.careType.iconResId)
                        binding.textViewCareRecyclerMainTitle.text =
                            context.getString(careMainModel.careType.titleResId)*/

            filterUI()

            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
    }

    private fun filterUI() {
        when (careMainModel.careType) {
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
        }
    }
}
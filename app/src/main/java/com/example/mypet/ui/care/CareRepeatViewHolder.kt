package com.example.mypet.ui.care

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareRecyclerRepeatBinding
import com.example.mypet.domain.care.CareViewHolderRepeatModel

class CareRepeatViewHolder(
    private val binding: FragmentCareRecyclerRepeatBinding,
    private val callback: CareRepeatCallback,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            callback.onItemClick()
        }
    }

    fun bind(foodRepeatModel: CareViewHolderRepeatModel) {
        //TODO дописать функуию отображения информации о повторе в зависимости от данных. Как пример, если установлены понедельник, вторник то "Каждый Пн, Вт", если через неделю то "Каждую 2 неделю по Пн, Вт". в таком роде, надо хорошо так подумать.
        binding.textViewCareRecyclerRepeatDescription.text = foodRepeatModel.description.ifEmpty {
            binding.root.context.getString(
                R.string.care_repeat_description_none
            )
        }
    }
}
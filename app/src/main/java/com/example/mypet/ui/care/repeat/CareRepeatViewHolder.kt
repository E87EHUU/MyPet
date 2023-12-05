package com.example.mypet.ui.care.repeat

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerRepeatBinding
import com.example.mypet.domain.care.CareRepeatModel

class CareRepeatViewHolder(
    private val binding: FragmentCareRecyclerRepeatBinding,
    private val callback: CareRepeatCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    private lateinit var careRepeatModel: CareRepeatModel



    fun bind(careRepeatModel: CareRepeatModel?) {
        careRepeatModel?.let {
            this.careRepeatModel = careRepeatModel


            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
        //TODO дописать функуию отображения информации о повторе в зависимости от данных. Как пример, если установлены понедельник, вторник то "Каждый Пн, Вт", если через неделю то "Каждую 2 неделю по Пн, Вт". в таком роде, надо хорошо так подумать.
    }
}
package com.example.mypet.ui.pet.food.alarm

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetFoodRecyclerMainBinding
import com.example.mypet.domain.alarm.AlarmMinModel

class PetFoodAlarmViewHolder(
    private val binding: FragmentPetFoodRecyclerMainBinding,
    private val callback: PetFoodAlarmCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var alarmMinModel: AlarmMinModel

    init {
        binding.root.setOnClickListener {
            callback.onClickAlarm(alarmMinModel)
        }
    }

    fun bind(alarmMinModel: AlarmMinModel) {
        this.alarmMinModel = alarmMinModel

        val iconResId = if (alarmMinModel.isActive) R.drawable.baseline_notifications_24
        else R.drawable.baseline_notifications_off_24
        binding.textViewPetFoodRecyclerItemIcon.setImageResource(iconResId)
        binding.textViewPetFoodRecyclerItemTime.text = alarmMinModel.time
    }
}
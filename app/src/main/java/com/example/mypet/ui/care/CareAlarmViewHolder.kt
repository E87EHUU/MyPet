package com.example.mypet.ui.care

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmBinding
import com.example.mypet.domain.care.CareViewHolderAlarmModel

class CareAlarmViewHolder(
    private val binding: FragmentCareRecyclerAlarmBinding,
    private val callback: CareAlarmCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var careAlarmModel: CareViewHolderAlarmModel

    init {
        binding.root.setOnClickListener {
            callback.onItemClick(careAlarmModel)
        }
    }

    fun bind(careAlarmModel: CareViewHolderAlarmModel) {
        this.careAlarmModel = careAlarmModel

        // TODO добавить событие на клик по времени, чтобы открывался пикер времени, устанавливать время и обновлять в изменяымх полях и последующие использование данных при сохранении модели.

        // TODO добавить меню по трем точкам - Удаление, отключение\включение, изменить(дублирование тапа по общей плитке)

        // TODO добавить кнопку "добавить" внизу списка будильников, или еще как. Общий фаб не очень понятен, и возможно несколько добавлений в фрагменте. либо мультифаб.. надо думать.

        // TODO добавить ограничение на вводимые данные в поле количества раз до разумного. Больше 0 и меньше 10 ???
        with(careAlarmModel) {
            // binding.textViewCareRecyclerAlarmDescription.text = a

            binding.textViewCareRecyclerAlarmTime.text = time

//            binding.switchFoodItemActive.isVisible = alarmId != null
//            binding.switchFoodItemActive.isChecked = alarmIsActive ?: false
        }
    }
}
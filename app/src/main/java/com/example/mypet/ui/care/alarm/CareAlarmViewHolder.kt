package com.example.mypet.ui.care.alarm

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmBinding
import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.ui.care.alarm.main.CareAlarmMainAdapter

class CareAlarmViewHolder(
    private val binding: FragmentCareRecyclerAlarmBinding,
    private val callback: CareAlarmCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    private lateinit var careAlarmModel: CareAlarmModel
    private val adapter = CareAlarmMainAdapter(callback)

    init {
        binding.recyclerViewCareRecyclerAlarmRecycler.adapter = adapter
        binding.root.setOnClickListener {
            //callback.onClickAlarm(careAlarmModel)
        }
    }

    fun bind(careAlarmModel: CareAlarmModel?) {
        careAlarmModel?.let {
            this.careAlarmModel = careAlarmModel

            adapter.submitList(careAlarmModel.alarms)

            // binding.textViewCareRecyclerAlarmDescription.text = a

//            binding.textViewCareRecyclerAlarmTime.text = time

//            binding.switchFoodItemActive.isVisible = alarmId != null
//            binding.switchFoodItemActive.isChecked = alarmIsActive ?: false

            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
        // TODO добавить событие на клик по времени, чтобы открывался пикер времени, устанавливать время и обновлять в изменяымх полях и последующие использование данных при сохранении модели.

        // TODO добавить меню по трем точкам - Удаление, отключение\включение, изменить(дублирование тапа по общей плитке)

        // TODO добавить кнопку "добавить" внизу списка будильников, или еще как. Общий фаб не очень понятен, и возможно несколько добавлений в фрагменте. либо мультифаб.. надо думать.

        // TODO добавить ограничение на вводимые данные в поле количества раз до разумного. Больше 0 и меньше 10 ???
    }
}